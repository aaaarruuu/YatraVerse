"""Load knowledge_base/monuments/*.txt into the Neon `chunks` table.

Run from the ai-service folder:
    python -m app.rag.ingest

Needs the DATABASE_URL environment variable (Neon connection string).
"""
import os
import pathlib

import psycopg
from pgvector.psycopg import register_vector
from sentence_transformers import SentenceTransformer

DB_URL = os.environ["DATABASE_URL"]
KB_DIR = pathlib.Path(__file__).resolve().parents[3] / "knowledge_base" / "monuments"
MODEL_NAME = "BAAI/bge-m3"
MAX_CHARS = 1200


def parse_file(path):
    """Return (metadata dict, list of (section, text)) for one knowledge file."""
    raw = path.read_text(encoding="utf-8").replace("\r\n", "\n")
    header, _, body = raw.partition("\n\n")
    meta = {}
    for line in header.splitlines():
        if ":" in line:
            key, value = line.split(":", 1)
            meta[key.strip()] = value.strip()
    sections = []
    for block in body.split("\n## "):
        block = block.strip()
        if block.startswith("## "):
            block = block[3:]
        if not block:
            continue
        title, _, text = block.partition("\n")
        sections.append((title.strip(), text.strip()))
    return meta, sections


def split_text(text):
    """Keep a section whole when small, otherwise split on paragraphs."""
    if len(text) <= MAX_CHARS:
        return [text]
    pieces, current = [], ""
    for para in text.split("\n\n"):
        if current and len(current) + len(para) + 2 > MAX_CHARS:
            pieces.append(current)
            current = para
        else:
            current = f"{current}\n\n{para}".strip()
    if current:
        pieces.append(current)
    return pieces


def build_chunks(path):
    meta, sections = parse_file(path)
    rows = []
    for section, text in sections:
        for piece in split_text(text):
            rows.append({
                "destination": meta.get("destination"),
                "city": meta.get("city"),
                "state": meta.get("state"),
                "category": meta.get("category"),
                "section": section,
                "source": path.name,
                "content": f"{meta.get('destination')} - {section}\n{piece}",
            })
    return rows


def main():
    files = sorted(KB_DIR.glob("*.txt"))
    if not files:
        raise SystemExit(f"No .txt files found in {KB_DIR}")

    print("Loading BGE-M3 (first run downloads about 2 GB)...")
    model = SentenceTransformer(MODEL_NAME)
    model.max_seq_length = 512

    with psycopg.connect(DB_URL) as conn:
        register_vector(conn)
        total = 0
        for path in files:
            rows = build_chunks(path)
            vectors = model.encode(
                [r["content"] for r in rows],
                normalize_embeddings=True,
                batch_size=8,
            )
            with conn.cursor() as cur:
                cur.execute("DELETE FROM chunks WHERE source = %s", (path.name,))
                for row, vec in zip(rows, vectors):
                    cur.execute(
                        """INSERT INTO chunks
                           (destination, city, state, category, section, source, content, embedding)
                           VALUES (%s, %s, %s, %s, %s, %s, %s, %s)""",
                        (row["destination"], row["city"], row["state"], row["category"],
                         row["section"], row["source"], row["content"], vec),
                    )
            conn.commit()
            total += len(rows)
            print(f"{path.name}: {len(rows)} chunks")
        print(f"Done. {total} chunks stored.")


if __name__ == "__main__":
    main()
