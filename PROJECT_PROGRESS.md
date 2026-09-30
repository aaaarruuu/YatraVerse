# YatraVerse AI - Project Progress

Last updated: 2026-09-30 (signup added, Android connected to live backend)

## Current Status

The Spring Boot backend (JWT auth + destinations API) is built and **deployed
on Render** with a Render PostgreSQL database. The Android app is connected to
the live backend: login and signup both work end-to-end, and the Explore
screen lists the seeded destinations. The ai-service, ml and llm modules
still have no code.

The whole project is being kept free to run and deploy (see "Free
Deployment" below). The tech stack itself is unchanged.

## Current Step

Step 4: Explore images. Destinations have imageUrl = null, so add real,
freely usable image URLs and a small backend update endpoint to save them.

## Completed

- Repository scaffolding, docker-compose.yml (postgres + minio).
- Android app (package com.yatraverse): Splash, Login, Signup, Home, Explore,
  Bookings, Community and Profile fragments, bottom navigation, nav graph,
  Retrofit client, ApiService, SessionManager. Runs on a Pixel 5 emulator
  (API 34).
- Android now points at the live Render backend (base URL changed from the
  local one).
- Signup screen added (SignupRequest model, ApiService.signup ->
  POST /api/auth/signup, fragment_signup.xml, SignupFragment, nav_graph
  action login -> signup -> home, "New here? Create an account" link on the
  login screen). Tested: a new account can be created and lands on Home.
- Explore screen: RecyclerView of destination cards with Glide already
  installed. 4 destinations exist in the database, all with imageUrl = null.
- Spring Boot backend (Spring Boot 4.1.1, Java 25, Maven wrapper):
  - JPA entities: User, Destination; repositories for both.
  - AuthController + AuthService (signup/login), JwtUtil, JwtAuthFilter,
    SecurityConfig.
  - DestinationController (destinations list).
  - Config comes from environment variables.
  - Verified locally: app starts, connects to Postgres, login and destination
    queries run.
- backend/Dockerfile added.
- feature/android-project-skeleton merged into `main` (fast-forward) and
  pushed, so `main` now holds the backend, Dockerfile and Android skeleton.
- Render deployment:
  - PostgreSQL database `yatraverse-db` (PostgreSQL 18, Singapore).
  - Web service built from `main` with Docker; logs show "Your service is
    live". Tomcat started on port 10000 (Render's PORT).
  - Public URL: https://yatraverse-bnv5.onrender.com

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

Lesson learned: "No matching entries" in Render's Dockerfile Path field meant
the Dockerfile was not on the selected branch (`main`) yet.

## Not Done Yet

- The database password was shared in a chat and must be reset in Render
  (yatraverse-db), then POSTGRES_PASSWORD updated in the web service.
- Destination images: imageUrl is null for all 4 destinations. No update
  endpoint exists yet (needs a small PUT addition).
- Refresh tokens, roles, WebSocket, MinIO/R2 storage.
- pgvector is not enabled anywhere yet.
- FastAPI AI service, RAG pipeline, Qwen3 8B, BGE-M3, ML, Heritage Scanner.
- Neon migration (the Render free database is temporary; check its expiry
  date in the Render dashboard).
- Tests.

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
  android-app/            (Gradle project, app/ module, skeleton screens)
  backend/                (Spring Boot: Dockerfile, pom.xml, mvnw, src/)
  ai-service/             (empty - .gitkeep only)
  llm/checkpoints/        (empty - .gitkeep only)
  ml/                     (empty - .gitkeep only)
  data/raw|processed|instruction/   (empty - .gitkeep only)
  knowledge_base/         (empty - .gitkeep only)
  docs/                   (empty - .gitkeep only)
  tests/                  (empty - .gitkeep only)

## Free Deployment Plan

| Piece | Local | Free deploy | Status |
|-------|-------|-------------|--------|
| Postgres | local Postgres / docker compose | Render PostgreSQL now, Neon later (pgvector) | Render DB live |
| Object storage | MinIO | Cloudflare R2 | not started |
| Spring Boot backend | ./mvnw spring-boot:run | Render (free web service, Docker) | live |
| Android app | Emulator | APK via GitHub Releases | not started |
| AI service (Qwen3 8B, BGE-M3) | Own machine | Laptop + tunnel, or free notebook GPU (demo only) | not started |

All host-specific values come from environment variables, so switching
host is a config change, not a code change. Verify free-tier limits
before relying on them; they change often. The free backend sleeps when
idle, and its first request can take about a minute or more.

## Next Steps (in order)

1. Reset the Render database password, then update POSTGRES_PASSWORD in the
   web service settings (still open; the old password was posted in a chat).
2. Explore images: find real, freely usable image URLs, add a small backend
   PUT endpoint to update a destination's imageUrl, and save the URLs.
3. Add more destinations and richer detail data.
4. Add refresh tokens and roles.
5. Cap JVM memory for the 512 MB free instance (JAVA_TOOL_OPTIONS=-Xmx350m).
6. Create a Neon project, enable pgvector, and move the database before the
   Render database expires.
7. Later: MinIO locally and R2 when deployed; FastAPI AI service via laptop
   tunnel; RAG; ML features.

## How to Continue

1. git checkout main && git pull
2. Read README.md ("Free Deployment" section) and this file.
3. Do the next unfinished item above. For new work, create a feature branch
   from main, and merge back into main to trigger a Render redeploy (Render
   deploys from main).
4. Claude explains each step before writing code.
