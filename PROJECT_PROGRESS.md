# YatraVerse AI - Project Progress

Last updated: 2026-10-03 (RAG chat working end to end locally, 9 monuments in the knowledge base)

## Current Status

The Spring Boot backend (JWT auth, destinations API, POST /api/chat) is deployed
on Render with a Render PostgreSQL database. The Android app is connected to
the live backend: login, signup, Explore (9 destinations with images), the
destination detail screen and an AI chat screen all work.

The RAG chat works end to end on the dev laptop: Android chat screen ->
Spring Boot /api/chat -> FastAPI /chat -> BGE-M3 + Neon pgvector search ->
Qwen3 4B (Ollama) -> grounded answer with sources. The AI service is not
deployed yet, so chat only works while the laptop is running it.

The whole project is being kept free to run and deploy (see "Free
Deployment Plan" below). The RAG database is Neon (pgvector), not local Docker.

## Current Step

Step 7: deploy the AI service (Cloudflare Tunnel or a free GPU notebook) and
point the Render backend at it.

## Completed

- Repository scaffolding, docker-compose.yml (postgres + minio).
- Android app (package com.yatraverse): Splash, Login, Signup, Home, Explore,
  Destination detail, Chat, Bookings, Community and Profile fragments, bottom
  navigation, nav graph, Retrofit client, ApiService, SessionManager.
  Runs on a Pixel 5 emulator.
- Android points at the live Render backend. Signup screen, login loader
  (spinner and "waking up the server" hint for the free-tier cold start).
- Explore screen: RecyclerView of destination cards with Glide. 9 destinations
  (Taj Mahal, Khajuraho, Konark, Sanchi, Ajanta, Ellora, Hampi, Orchha, Mandu),
  all with real Wikimedia images saved through the authenticated
  PUT /api/destinations/{id}.
- Destination detail: GET /api/destinations/{id} (404 for unknown ids). The
  detail screen has an "Ask AI" button that opens the chat for that place.
- Spring Boot backend (Spring Boot 4.1.1, Java 25, Maven wrapper): JPA entities
  User and Destination, JWT auth (signup/login, JwtUtil, JwtAuthFilter,
  SecurityConfig), DestinationController, POST /api/chat (forwards to the AI
  service). Config comes from environment variables.
- Render deployment: PostgreSQL `yatraverse-db` and a Docker web service built
  from `main`. Public URL: https://yatraverse-bnv5.onrender.com.
  JAVA_TOOL_OPTIONS=-Xmx350m, Auto-Deploy On Commit. Render database password
  rotated.
- RAG (ai-service/app/rag):
  - Neon project `yatraverse-sg` (Singapore), `chunks` table with vector(1024)
    and an HNSW index.
  - Knowledge base: 9 monument files in knowledge_base/monuments (55 chunks).
    Facts were written from general knowledge and should be checked against
    the ASI website, especially closing days.
  - ingest.py (section-aware chunking, BGE-M3 embeddings, delete-and-reload per
    file), retrieve.py (destination-name filter, MIN_SCORE 0.45), answer.py
    (greeting and thanks replies, retry with the place name for vague
    questions, grounded prompt, "I don't know" fallback).
  - LLM: Qwen3 4B through Ollama (laptop RTX 4050, 6 GB VRAM). This replaces
    the planned Qwen3 8B Transformers setup because of the VRAM limit.
  - FastAPI /chat endpoint (uvicorn, port 8000).
  - Tested in the terminal and in the app: greeting, "Is it open at 5 PM?"
    (Khajuraho), closing days (Ellora), history (Ajanta), Jahaz Mahal (Mandu),
    Vittala Temple (Hampi), railway station (Orchha), 1561 capture (Mandu),
    and an off-topic question ("pasta carbonara") correctly gets "I don't know".
- Dev machine: i7-13645HX, 16 GB RAM, RTX 4050 6 GB, Python 3.12. Docker
  Desktop is installed but virtualization is off in the BIOS, so Neon is used
  instead of local Docker.

## Render Configuration (working)

| Field | Value |
|-------|-------|
| Language | Docker |
| Branch | main |
| Region | Singapore |
| Root Directory | empty |
| Dockerfile Path | backend/Dockerfile |
| Docker Build Context Directory | backend |
| Docker Command | empty |
| Instance type | Free (0.1 CPU, 512 MB RAM) |

Environment variables (names only, never store values in the repo):
POSTGRES_URL (JDBC form of the Internal Database URL, no user/password inside),
POSTGRES_USERNAME, POSTGRES_PASSWORD, JWT_SECRET, JWT_EXPIRATION (86400000).

Lessons learned:
- "No matching entries" in Render's Dockerfile Path field meant the Dockerfile
  was not on the selected branch (`main`) yet.
- A failed Render build keeps the old version live.
- Never paste a command that sets DATABASE_URL into chat. Paste output only.
  Environment variables do not carry over between terminals, so set
  DATABASE_URL in each terminal that needs it.

## Not Done Yet

- Deploy the AI service (Cloudflare Tunnel or a free GPU notebook) and point
  the Render backend at it.
- Refresh tokens and roles (POST and PUT on destinations should be admin-only).
- Move app data from Render Postgres (temporary) to Neon before it expires.
- Tourism analytics (admin): an events table (destination views, chat
  questions, grounded or not, response time), bookings backend, admin-only
  stats endpoints, and a dashboard (web page or Android admin screen).
  Depends on roles.
- Bookings backend, WebSocket, MinIO/R2 storage.
- Trip planner, Heritage Scanner, ML features, tests.
- Release the app as an APK through GitHub Releases.
- Git cleanup: commit and merge feature/rag-knowledge-base into `main`; delete
  or ignore android-app/_backup_before_signup/; decide on the
  WHY_TO_USE_THIS_STACK edit.
- Later RAG improvements: hybrid search, reranker.

## Existing Project Structure

YatraVerse/
  README.md
  WHY_TO_USE_THIS_STACK
  PROJECT_PROGRESS.md
  CONTRIBUTING.md
  LICENSE
  .gitignore
  .env.example
  docker-compose.yml
  android-app/            (Gradle project, app/ module, screens incl. chat)
  backend/                (Spring Boot: Dockerfile, pom.xml, mvnw, src/)
  ai-service/             (app/rag/: ingest.py, retrieve.py, answer.py; app/main.py FastAPI)
  llm/checkpoints/        (empty - .gitkeep only)
  ml/                     (empty - .gitkeep only)
  data/raw|processed|instruction/   (empty - .gitkeep only)
  knowledge_base/         (monuments/: 9 files)
  docs/                   (empty - .gitkeep only)
  tests/                  (empty - .gitkeep only)

## Free Deployment Plan

| Piece | Local | Free deploy | Status |
|-------|-------|-------------|--------|
| Postgres | Neon (or local Postgres install) | Render PostgreSQL now (backend, temporary); Neon for RAG (pgvector) | Render DB live; Neon live for RAG |
| Object storage | MinIO | Cloudflare R2 | not started |
| Spring Boot backend | ./mvnw spring-boot:run | Render (free web service, Docker) | live |
| Android app | Emulator | APK via GitHub Releases | not started |
| AI service (Qwen3 4B, BGE-M3) | Own PC (RTX 4050 6 GB) | Cloudflare Tunnel | not started |
| Vector DB (pgvector) | Neon | Neon free tier | live |

All host-specific values come from environment variables, so switching
host is a config change, not a code change. Verify free-tier limits
before relying on them; they change often. The free backend sleeps when
idle, and its first request can take about a minute or more.

## Option: Self-host on an old laptop

Considered: 8 GB RAM / SSD laptop running Docker (Spring Boot, Postgres with
pgvector, MinIO) exposed with a Cloudflare Tunnel. Not enough for Qwen3.
Pros: no cold starts, no expiring DB. Cons: own uptime, backups, security; a
quick-tunnel URL changes on restart. No code change needed, only env vars and
BASE_URL in RetrofitClient.kt. Decision pending; Render stays for now.

## Next Steps (in order)

1. Commit the RAG work and merge it into `main` (git cleanup).
2. Deploy the AI service (Cloudflare Tunnel) and point the Render backend at it.
3. Refresh tokens and roles (admin-only POST and PUT on destinations).
4. Add an events table to log destination views and chat questions (so
   analytics has data from day one).
5. Bookings backend, then admin stats endpoints and the analytics dashboard.
6. Move app data from Render Postgres to Neon.
7. Release an APK through GitHub Releases.
8. Later: hybrid search, reranker, MinIO/R2, WebSocket, trip planner,
   Heritage Scanner, ML features, tests.

## How to Continue

1. git checkout main && git pull
2. Read README.md ("Free Deployment" section) and this file.
3. Do the next unfinished item above. For new work, create a feature branch
   from main, and merge back into main to trigger a Render redeploy (Render
   deploys from main).
4. To run the RAG chat locally: start Ollama (qwen3:4b), set DATABASE_URL in
   the ai-service terminal, run `uvicorn app.main:app --host 0.0.0.0 --port 8000`,
   run the backend with `.\mvnw spring-boot:run`, then run the app from Android Studio.
5. Claude explains each step before writing code.