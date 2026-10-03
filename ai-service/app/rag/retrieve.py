"""Search the Neon `chunks` table.

Run from the ai-service folder:
    python -m app.rag.retrieve "your question"
    python -m app.rag.retrieve "your question" Konark     (optional place filter)

The optional filter matches a chunk's city, or any destination name
contained in the filter text (so "Khajuraho Temples" matches "Khajuraho").
Needs the DATABASE_URL environment variable (Neon connection string).
"""
import os
import sys
from functools import lru_cache

import psycopg
from pgvector.psycopg import register_vector
from sentence_transformers import SentenceTransformer

DB_URL = os.environ["DATABASE_URL"]
MODEL_NAME = "BAAI/bge-m3"
TOP_K = 5
MIN_SCORE = 0.45  # confirmed: noise <= 0.37, correct answers >= 0.576


@lru_cache(maxsize=1)
def get_model():
    return SentenceTransformer(MODEL_NAME)


def search(query, city=None, k=TOP_K):
    model = get_model()
    qvec = model.encode(query, normalize_embeddings=True)
    where = (
        "WHERE lower(city) = lower(%(city)s) "
        "OR strpos(lower(%(city)s), lower(destination)) > 0"
    ) if city else ""
    sql = f"""
        SELECT destination, city, section, content,
               1 - (embedding <=> %(q)s) AS score
        FROM chunks
        {where}
        ORDER BY embedding <=> %(q)s
        LIMIT %(k)s
    """
    with psycopg.connect(DB_URL) as conn:
        register_vector(conn)
        with conn.cursor() as cur:
            cur.execute(sql, {"q": qvec, "city": city, "k": k})
            return cur.fetchall()


def main():
    if len(sys.argv) < 2:
        raise SystemExit('Usage: python -m app.rag.retrieve "question" [place]')
    sys.stdout.reconfigure(encoding="utf-8")
    query = sys.argv[1]
    city = sys.argv[2] if len(sys.argv) > 2 else None

    results = search(query, city)
    print(f"\nQuestion: {query}" + (f"   (place filter: {city})" if city else ""))
    if not results:
        print("No chunks found.")
        return
    for i, (dest, c, section, content, score) in enumerate(results, 1):
        flag = "" if score >= MIN_SCORE else "  [below threshold]"
        print(f"\n{i}. score={score:.3f}  {dest} / {section} ({c}){flag}")
        print("   " + content.replace("\n", " ")[:300] + "...")
    if results[0][4] < MIN_SCORE:
        print("\nBest score is below the threshold, so the chatbot would answer: I don't know.")


if __name__ == "__main__":
    main()