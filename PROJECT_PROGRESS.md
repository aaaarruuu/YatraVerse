# YatraVerse AI - Project Progress

## Current Status

Repo scaffolding is complete and the Android app runs on an emulator.
The backend, ai-service, ml and llm modules still have no code.

The whole project is being kept free to run and deploy (see "Free
Deployment Plan" below). The tech stack itself is unchanged.

## Current Step

Step 2: Verify local infrastructure, then bootstrap the Spring Boot backend.

## Completed

- Repository scaffolding on branch feature/repo-scaffolding (committed locally).
- docker-compose.yml with postgres (pgvector) and minio, plus a helper
  that creates the media bucket.
- Android app (package com.yatraverse) installs and launches on a
  Pixel 5 emulator, API 34 (Google APIs).
- Free-deployment plan written into README.md.

## Not Done Yet

- feature/repo-scaffolding is not pushed to GitHub.
- `docker compose up` has not been confirmed working.
- Android project location in the repo is unconfirmed (android-app/ was
  still empty in the last scaffolding commit).
- Spring Boot backend, FastAPI AI service, database schema, JWT auth.

## Existing Project Structure

YatraVerse/
  README.md
  WHY_TO_USE_THIS_STACK
  LICENSE
  CONTRIBUTING.md
  .gitignore
  .env.example
  docker-compose.yml
  PROJECT_PROGRESS.md
  android-app/            (confirm the Android project is here)
  backend/                (empty - .gitkeep only)
  ai-service/             (empty - .gitkeep only)
  llm/checkpoints/        (empty - .gitkeep only)
  ml/                     (empty - .gitkeep only)
  data/raw|processed|instruction/   (empty - .gitkeep only)
  knowledge_base/         (empty - .gitkeep only)
  docs/                   (empty - .gitkeep only)
  tests/                  (empty - .gitkeep only)

## Free Deployment Plan

| Piece | Local | Free deploy |
|-------|-------|-------------|
| Postgres + pgvector | docker compose | Neon |
| Object storage | MinIO | Cloudflare R2 |
| Spring Boot backend | mvn spring-boot:run | Render (free web service) |
| Android app | Emulator | APK via GitHub Releases |
| AI service (Qwen3 8B, BGE-M3) | Own machine | Laptop + tunnel, or free notebook GPU (demo only) |

All host-specific values come from environment variables, so switching
host is a config change, not a code change. Verify free-tier limits
before relying on them; they change often.

## Next Steps (in order)

1. Push feature/repo-scaffolding to GitHub.
2. Run `docker compose up -d` and confirm postgres and minio work.
3. Backend bootstrap: minimal Spring Boot, config from env variables.
4. Database schema: users table, pgvector enabled.
5. Create a free Neon project and confirm the schema works there.
6. JWT auth endpoints (signup, login, refresh).
7. Android login/signup screens.
8. First free deploy: backend on Render + Neon.
9. Later: R2 replaces MinIO when deployed; AI service via laptop tunnel.

## How to Continue

1. git fetch origin
2. git checkout feature/repo-scaffolding
3. Read README.md ("Free Deployment" section) and this file.
4. Do the next unfinished item above; Claude explains each step before
   writing code.