"""Grounded Q&A: retrieve chunks from Neon, answer with Qwen3 via Ollama.

Run from the ai-service folder:
    python -m app.rag.answer "your question"
    python -m app.rag.answer "your question" "Taj Mahal"     (optional place filter)

Needs DATABASE_URL set, and Ollama running with qwen3:4b pulled.
"""
import json
import re
import sys
import urllib.request

from app.rag.retrieve import MIN_SCORE, search

OLLAMA_URL = "http://localhost:11434/api/chat"
LLM_MODEL = "qwen3:4b"
MAX_CHUNKS = 5
MIN_SCORE_WITH_CONTEXT = 0.55  # stricter bar when the place name is added to the query
IDK = "I don't know based on the information I have."
GREETING_REPLY = (
    "Hello! I'm the YatraVerse guide. Ask me about a monument's history, "
    "visiting hours, best time to visit, or how to get there."
)
THANKS_REPLY = "You're welcome! Ask me anything else about the destination."

GREETING = re.compile(
    r"^\s*(hi+|hello+|hey+|namaste|namaskar|good\s+(morning|afternoon|evening))\W*$",
    re.IGNORECASE,
)
THANKS = re.compile(r"^\s*(thanks?|thank\s+you|thx)\W*$", re.IGNORECASE)

SYSTEM_PROMPT = (
    "You are a travel assistant for Indian heritage destinations. "
    "Answer the question using ONLY the context provided. "
    "If the context does not contain the answer, reply exactly: "
    f"{IDK} "
    "You may reason simply from the context, for example compare a time of day with opening hours. If the context gives sunrise to sunset hours, say the place is open then, and note that sunset time changes with the season. "
    "Do not use outside knowledge. Do not guess. "
    "Keep the answer short and clear (1 to 4 sentences)."
)


def _clean(text):
    """Remove any leftover reasoning text from Qwen3."""
    if "</think>" in text:
        text = text.rsplit("</think>", 1)[1]
    text = re.sub(r"<think>.*?</think>", "", text, flags=re.DOTALL)
    return text.replace("<think>", "").strip()


def _ask_llm(question, chunks, place=None):
    context = "\n\n".join(
        f"[{i}] {dest} / {section}\n{content}"
        for i, (dest, _city, section, content, _score) in enumerate(chunks, 1)
    )
    viewing = f"The user is currently viewing: {place}.\n\n" if place else ""
    user_msg = f"Context:\n{context}\n\n{viewing}Question: {question}\n\n/no_think"
    payload = {
        "model": LLM_MODEL,
        "messages": [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": user_msg},
        ],
        "stream": False,
        "keep_alive": "10m",
        "options": {"temperature": 0.2, "num_ctx": 4096},
    }
    req = urllib.request.Request(
        OLLAMA_URL,
        data=json.dumps(payload).encode("utf-8"),
        headers={"Content-Type": "application/json"},
    )
    with urllib.request.urlopen(req, timeout=180) as resp:
        data = json.loads(resp.read().decode("utf-8"))
    return _clean(data["message"]["content"])


def answer(question, city=None):
    q = question.strip()
    if GREETING.match(q):
        return {"answer": GREETING_REPLY, "grounded": False, "sources": []}
    if THANKS.match(q):
        return {"answer": THANKS_REPLY, "grounded": False, "sources": []}

    results = search(q, city)
    chunks = [r for r in results if r[4] >= MIN_SCORE]

    # Vague follow-ups ("Is it open at 5 PM?"): retry with the place name added.
    if not chunks and city:
        results = search(f"{city}: {q}", city)
        chunks = [r for r in results if r[4] >= MIN_SCORE_WITH_CONTEXT]

    if not chunks:
        return {"answer": IDK, "grounded": False, "sources": []}

    chunks = chunks[:MAX_CHUNKS]
    text = _ask_llm(q, chunks, city)
    sources = [
        {"destination": d, "city": c, "section": s, "score": round(float(sc), 3)}
        for d, c, s, _content, sc in chunks
    ]
    return {"answer": text, "grounded": True, "sources": sources}


def main():
    if len(sys.argv) < 2:
        raise SystemExit('Usage: python -m app.rag.answer "question" [place]')
    sys.stdout.reconfigure(encoding="utf-8")
    question = sys.argv[1]
    city = sys.argv[2] if len(sys.argv) > 2 else None

    result = answer(question, city)
    print(f"\nQuestion: {question}" + (f"   (place filter: {city})" if city else ""))
    print(f"\nAnswer: {result['answer']}")
    if result["sources"]:
        print("\nSources:")
        for s in result["sources"]:
            print(f"  - {s['destination']} / {s['section']} (score {s['score']})")


if __name__ == "__main__":
    main()