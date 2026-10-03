"""YatraVerse AI service.

Run from the ai-service folder:
    uvicorn app.main:app --host 0.0.0.0 --port 8000

Needs DATABASE_URL set, and Ollama running with qwen3:4b pulled.
"""
from contextlib import asynccontextmanager
from typing import Optional

from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from app.rag.answer import answer
from app.rag.retrieve import get_model


@asynccontextmanager
async def lifespan(app: FastAPI):
    get_model()  # load BGE-M3 once at startup
    yield


app = FastAPI(title="YatraVerse AI Service", lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)


class ChatRequest(BaseModel):
    question: str = Field(..., min_length=1, max_length=500)
    city: Optional[str] = None


class Source(BaseModel):
    destination: str
    city: str
    section: str
    score: float


class ChatResponse(BaseModel):
    answer: str
    grounded: bool
    sources: list[Source]


@app.get("/health")
def health():
    return {"status": "ok"}


@app.post("/chat", response_model=ChatResponse)
def chat(req: ChatRequest):
    question = req.question.strip()
    if not question:
        raise HTTPException(status_code=400, detail="question is empty")
    city = req.city.strip() if req.city and req.city.strip() else None
    try:
        return answer(question, city)
    except Exception as e:
        raise HTTPException(status_code=503, detail=f"AI service error: {type(e).__name__}")