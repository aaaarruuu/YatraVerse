# YatraVerse AI - Project Progress

Last updated: 2026-10-01 (destination images done, DB password rotated)

## Current Status

The Spring Boot backend (JWT auth + destinations API) is built and **deployed
on Render** with a Render PostgreSQL database. The Android app is connected to
the live backend: login and signup both work end-to-end, and the Explore
screen lists the seeded destinations with real images. The ai-service, ml and llm modules
still have no code.

The whole project is being kept free to run and deploy (see "Free
Deployment" below). The tech stack itself is unchanged.

## Current Step

Step 5: Destination detail. Add GET /api/destinations/{id}, a detail screen
in Android opened by tapping an Explore card, and more destinations.

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
- Explore screen: RecyclerView of destination cards with Glide. 4
  destinations exist, all now with real images.
- Destination images: authenticated PUT /api/destinations/{id} saves an
  imageUrl. Wikimedia Commons 960px thumbnails (via the Wikipedia REST summary
  API) were saved for all 4 destinations and show in the app.
- Render deploy was stale (old commit 62387fc, no PUT route, so saves gave
  403). Fixed with Manual Deploy of commit 962f04d. Auto-Deploy can be set to
  "On Commit" in Settings > Build & Deploy.
- Render database password reset and POSTGRES_PASSWORD updated.
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

- JAVA_TOOL_OPTIONS=-Xmx350m not set yet on the Render web service.
- Destination detail endpoint and screen; only 4 destinations exist.
- Uncommitted local items: WHY_TO_USE_THIS_STACK edit and
  android-app/_backup_before_signup/ (delete or add to .gitignore).
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

## Option: Self-host on an old laptop

Considered: 8 GB RAM / SSD laptop running Docker (Spring Boot, Postgres with
pgvector, MinIO) exposed with a Cloudflare Tunnel. Not enough for Qwen3 8B.
Pros: no cold starts, no expiring DB. Cons: own uptime, backups, security; a
quick-tunnel URL changes on restart. No code change needed, only env vars and
BASE_URL in RetrofitClient.kt. Decision pending; Render stays for now.

## Next Steps (in order)

1. Set JAVA_TOOL_OPTIONS=-Xmx350m on the Render web service.
2. Add GET /api/destinations/{id}, a detail screen in Android, and more
   destinations (Ajanta, Ellora, Hampi, Orchha, ...).
3. Add refresh tokens and roles.
4. Decide: Neon (enable pgvector) or self-hosted Postgres before the Render
   database expires.
5. Later: MinIO locally and R2 when deployed; FastAPI AI service via laptop
   tunnel; RAG; ML features.

## How to Continue

1. git checkout main && git pull
2. Read README.md ("Free Deployment" section) and this file.
3. Do the next unfinished item above. For new work, create a feature branch
   from main, and merge back into main to trigger a Render redeploy (Render
   deploys from main).
4. Claude explains each step before writing code.
