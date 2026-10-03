# 🌍 YatraVerse AI

> **AI-Powered Smart Tourism Ecosystem**

> 💸 **Zero-cost by design:** the database runs on **Neon** (free Postgres with pgvector), the AI models run locally, and everything deploys on free tiers. No local Docker is needed. See [Free Deployment](#-free-deployment).

YatraVerse AI is an AI-powered smart tourism ecosystem that combines **personalized trip planning, intelligent itinerary generation, heritage exploration, tourist safety, sustainable travel, local experiences, community interaction, and tourism analytics** into a single Android application.

The project is also designed as a **learning-focused AI engineering project**, where the team builds a full RAG (Retrieval-Augmented Generation) pipeline on top of an open-weight LLM (**Qwen3 8B**), along with embeddings, vector search, and supporting AI/ML services — with a strong emphasis on understanding how each layer of the stack actually works rather than relying on managed/vendor services wherever a learning opportunity exists.

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Technology Decisions](#-technology-decisions--why)
- [Project Objectives](#-project-objectives)
- [Key Features](#-key-features)
- [System Architecture](#-system-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [LLM Service — Qwen3 8B](#-llm-service--qwen3-8b)
- [RAG Architecture](#-rag-architecture)
- [AI/ML Components](#-aiml-components)
- [Android Application](#-android-application)
- [Backend Architecture](#-backend-architecture)
- [Database](#-database)
- [Authentication](#-authentication)
- [Real-Time Notifications](#-real-time-notifications)
- [Object Storage](#-object-storage)
- [Maps](#-maps)
- [Team Collaboration](#-team-collaboration)
- [Git Workflow](#-git-workflow)
- [Installation](#-installation)
- [Running the Project](#-running-the-project)
- [Environment Variables](#-environment-variables)
- [Free Deployment](#-free-deployment)
- [Development Roadmap](#-development-roadmap)
- [Testing](#-testing)
- [Security](#-security)
- [Future Scope](#-future-scope)
- [Project Status](#-project-status)

---

# 🧭 Current Status (2026-10-03)

| Area | State |
|---|---|
| Android app | Splash, Login (with loader), Signup, Home, Explore (9 destinations), Destination detail with an "Ask AI" button, Chat screen (JWT, answers with sources), Bookings, Community, Profile |
| Backend | Spring Boot (JWT auth, destinations list + detail + image update, POST /api/chat forwarding to the AI service), deployed on Render (Docker). The chat endpoint works locally; the AI service is not deployed yet |
| Database | Render PostgreSQL (temporary) for app data. Neon (pgvector) for the RAG chunks table |
| Live URL | https://yatraverse-bnv5.onrender.com (free tier, slow first request) |
| AI service / RAG | FastAPI POST /chat: BGE-M3 embeddings, pgvector search filtered by destination, "I don't know" threshold (0.45), greeting replies, grounded answers from Qwen3 4B through Ollama on a laptop RTX 4050 (6 GB VRAM). Knowledge base: 9 monuments (55 chunks). Qwen3 4B via Ollama replaces the planned Qwen3 8B Transformers setup because of the 6 GB VRAM limit |
| ML, MinIO/R2, WebSocket | not started |

**Next:** deploy the AI service (Cloudflare Tunnel or a free GPU notebook), refresh tokens and roles, move app data to Neon, then trip planner, admin analytics and the other AI features.

---

# 🚀 Project Overview

YatraVerse AI aims to provide a complete digital tourism ecosystem where users can:

- Plan trips according to their budget and interests
- Generate optimized day-wise itineraries
- Identify historical monuments using images
- Learn about heritage and culture
- Discover local guides and experiences
- Book homestays and local services
- Get emergency assistance
- Share live location
- Track their sustainability score
- Receive crowd-level predictions
- Share travel experiences
- Read reviews
- Interact with the travel community

The project combines:

```text
Android Development
        +
Spring Boot
        +
Spring Security (JWT)
        +
PostgreSQL + PGVector
        +
MinIO (Object Storage)
        +
WebSocket (Real-time)
        +
OpenStreetMap / MapLibre
        +
Machine Learning
        +
Computer Vision
        +
Qwen3 8B (LLM)
        +
RAG
```

---

# 🔧 Technology Decisions — Why

The stack intentionally favors self-hosted, learnable components over fully managed vendor services, so the team gains hands-on engineering experience at every layer. Summary of key decisions:

| Feature | We use | Instead of | Reason |
|---|---|---|---|
| Authentication | **Spring Security + JWT + PostgreSQL** | Firebase Auth | Learn password hashing, JWT issuing/validation, security filters, roles, refresh tokens |
| Trip Planning | **Python + RAG** | Simple LLM API wrapper | Learn embeddings, retrieval, chunking, ranking, and prompt construction |
| Smart Itinerary | **Spring Boot + Python AI Service** | Spring Boot only | Learn microservice/API communication patterns |
| Heritage Scanner | **PyTorch + OpenCV** | Ready-made CV API | Learn image preprocessing, CNN/transfer learning, and inference |
| Tourism Knowledge | **RAG + PGVector** | Firebase/FAISS | Learn vector search combined with relational SQL and metadata filtering |
| Crowd Prediction | **Python + Scikit-learn/PyTorch** | External prediction API | Actually learn applied ML |
| Eco Score | **Java rule engine** | AI-generated scoring | Learn deterministic, auditable business logic |
| Maps | **OpenStreetMap + MapLibre** | Google Maps | Avoid vendor lock-in; learn geospatial/tile concepts |
| Notifications | **WebSocket + Android notifications** | Firebase Cloud Messaging | Learn real-time bidirectional communication |
| Image Storage | **MinIO (S3-compatible)** | Firebase Storage | Learn self-hosted object storage and S3 APIs |
| Main DB | **PostgreSQL** | Firebase/Firestore | Learn relational schema design at scale |
| Vector DB | **PGVector** | FAISS | Learn SQL + vector similarity in a single database |
| Mobile | **Android Studio + Kotlin** | — | Native Android development |
| Backend | **Spring Boot** | — | Strong backend engineering foundation |
| AI Service | **Python + FastAPI** | Flask | Typed, async-friendly API/service architecture |
| LLM | **Qwen3 8B (Transformers/PyTorch)** | Ollama-only wrapper | Learn actual model loading, tokenization, and inference, not just calling a CLI |
| Embeddings | **BGE-M3 (multilingual)** | Basic sentence-transformers model | Stronger retrieval quality and multilingual tourism support (Hindi/regional languages) |
| AI Framework | **Spring AI (selectively)** | Everything hand-rolled | Use it where it removes boilerplate, but understand the underlying HTTP/inference calls first |
| Hosting | **Local dev + free tiers (Neon, Render)** | Paid cloud | The whole project must run and deploy at $0; all hosts are configured through environment variables, so they are swappable without code changes |

> Firebase is no longer part of the core stack. It may optionally be reintroduced later for push notifications on top of WebSocket if offline delivery is required, but it is not a dependency for MVP.

---

# 🎯 Project Objectives

## 1. AI-Based Personalized Trip Planning

### Objective

Create personalized travel plans based on:

- Budget
- Destination
- Duration
- Interests
- Travel preferences
- Number of travelers

### Implementation

```text
Android Form / Chat
        ↓
Spring Boot API (JWT-secured)
        ↓
AI Service (FastAPI)
        ↓
RAG Retrieval (PGVector)
        ↓
Qwen3 8B (Transformers/PyTorch)
        ↓
Personalized Itinerary
        ↓
PostgreSQL
```

---

## 2. Smart Itinerary Generator

Generate optimized day-wise itineraries based on:

- Attraction timings
- Travel time
- Distance
- Weather
- Opening/closing times
- User preferences

### Architecture

```text
User Preferences
      ↓
Destination Data
      ↓
OpenStreetMap / MapLibre Routing
      ↓
Weather Information
      ↓
Optimization Logic
      ↓
AI Generated Itinerary
```

---

## 3. AI Heritage Scanner

The Heritage Scanner allows users to take a photograph of a monument and receive information about it.

### Features

- Monument recognition
- Historical information
- Architecture
- Location
- Visiting timings
- Entry information
- Cultural significance

### Architecture

```text
Android Camera
      ↓
Image (uploaded to MinIO)
      ↓
Vision Model (PyTorch + OpenCV)
      ↓
Monument Identification
      ↓
Tourism Knowledge Base (RAG + PGVector)
      ↓
Qwen3 8B
      ↓
Information Display
```

---

## 4. Local Experience Marketplace

Connect tourists with:

- Local guides
- Homestays
- Artisans
- Local food experiences
- Cultural experiences
- Tour operators

Features include:

- Business registration
- Listings
- Booking
- Ratings
- Reviews
- Availability

---

## 5. Tourist Safety & Emergency Assistance

The application provides:

- SOS button
- Emergency contacts
- Nearby hospitals
- Nearby police stations
- Live location sharing
- Emergency notifications (WebSocket, guaranteed delivery to connected clients)

### Architecture

```text
User
 ↓
SOS
 ↓
GPS Location
 ↓
Emergency Module (Spring Boot)
 ↓
WebSocket Broadcast + Backend Logic
 ↓
Emergency Contact
```

> Emergency functionality uses deterministic application logic and verified emergency information rather than relying on an LLM.

---

## 6. Eco Travel Score

YatraVerse calculates a sustainability score based on:

- Transportation
- Accommodation
- Distance
- Public transport usage
- Eco-friendly choices
- Travel behaviour

Example:

```text
Eco Travel Score: 82 / 100
```

The score is calculated using a **deterministic Java rule engine**, not by asking an LLM to perform the numerical calculation. The LLM may only be used to explain the score in natural language.

---

## 7. AI Crowd Prediction

Predict the expected crowd level at tourist destinations.

### Inputs

```text
Historical Visitor Data
Holiday
Day of Week
Weather
Season
Events
```

### Output

```text
LOW
MEDIUM
HIGH
```

Possible models:

- Random Forest
- XGBoost
- LightGBM
- LSTM

The LLM can explain the prediction, but the numerical prediction is handled by a dedicated ML model (Python, Scikit-learn/PyTorch).

---

## 8. Travel Community

Users can:

- Create posts
- Upload photos
- Write travel blogs
- Comment
- Like
- Review destinations
- Share experiences

Media files are stored in **MinIO** (S3-compatible object storage).

---

## 9. Tourism Analytics Dashboard

Admin dashboard provides:

- Visitor trends
- Popular destinations
- Booking trends
- User activity
- Reviews
- Destination popularity
- Crowd statistics
- Tourism feedback

---

# 🧩 Key Features

| Feature | Technology |
|---|---|
| User Authentication | Spring Security + JWT |
| Trip Planning | Qwen3 8B + RAG |
| Smart Itinerary | Spring Boot + AI Service |
| Heritage Scanner | Computer Vision (PyTorch + OpenCV) |
| Tourism Knowledge | RAG + PGVector |
| Crowd Prediction | ML (Scikit-learn/PyTorch) |
| Eco Score | Rule-based scoring (Java) |
| Maps | OpenStreetMap + MapLibre |
| Notifications | WebSocket + Android notifications |
| Image Storage | MinIO (S3-compatible) |
| Main Database | PostgreSQL |
| Vector Search | PostgreSQL + PGVector |
| Mobile Application | Android Studio (Kotlin) |
| Backend | Spring Boot |
| AI Service | Python (FastAPI) |
| LLM | Qwen3 8B (Transformers/PyTorch) |
| Embeddings | BGE-M3 (multilingual) |

---

# 🏗️ System Architecture

```text
                         YATRAVERSE AI
                              │
                              ▼
                  ┌─────────────────────┐
                  │   Android Mobile    │
                  │   Kotlin            │
                  │   Android Studio    │
                  └──────────┬──────────┘
                             │
                         REST / JSON / WebSocket
                             │
                             ▼
                  ┌─────────────────────┐
                  │    Spring Boot      │
                  │  Spring Security    │
                  │    REST Backend     │
                  └──────────┬──────────┘
                             │
            ┌────────────────┼────────────────┐
            │                │                │
            ▼                ▼                ▼
     ┌────────────┐   ┌────────────┐   ┌─────────────┐
     │ PostgreSQL │   │   MinIO    │   │ AI Service  │
     │ + PGVector │   │  Storage   │   │  (FastAPI)  │
     └────────────┘   └────────────┘   └──────┬──────┘
                                              │
                           ┌──────────────────┼──────────────────┐
                           │                  │                  │
                           ▼                  ▼                  ▼
                     ┌───────────┐     ┌────────────┐     ┌───────────┐
                     │  Qwen3 8B │     │    RAG     │     │ ML Models │
                     │           │     │ Retrieval  │     │           │
                     │Transform- │     │ + PGVector │     │ Crowd     │
                     │ers/PyTorch│     │  Search    │     │ Eco Score │
                     └───────────┘     └──────┬─────┘     └───────────┘
                                              │
                                              ▼
                                     ┌─────────────────┐
                                     │ Tourism         │
                                     │ Knowledge Base  │
                                     └─────────────────┘
```

---

# 🛠️ Technology Stack

## Android Frontend

The mobile application is developed using **Android Studio**.

### Technologies

- Kotlin
- Android SDK
- XML / Jetpack Compose
- Retrofit
- ViewModel
- Repository Pattern
- Navigation Component
- MapLibre GL Native (OpenStreetMap tiles)
- Android Location Services
- Android Camera APIs
- OkHttp WebSocket client (real-time notifications)
- JWT-aware Retrofit interceptors (auth token attach/refresh)

### Recommended Architecture

```text
Android
│
├── presentation/
│   ├── screens/
│   ├── adapters/
│   ├── viewmodels/
│   └── navigation/
│
├── data/
│   ├── api/
│   ├── ws/                  # WebSocket client
│   ├── models/
│   └── repository/
│
└── domain/
    ├── models/
    └── usecases/
```

---

# ☕ Backend

## Spring Boot

Spring Boot is responsible for the main business logic, authentication, and REST/WebSocket APIs.

### Responsibilities

- Authentication & authorization (JWT)
- User management
- Trip management
- Itinerary management
- Destination APIs
- Monument APIs
- Marketplace
- Booking
- Reviews
- Community
- Emergency services (WebSocket broadcast)
- Analytics
- Communication with AI service

### Technologies

```text
Java 25
Spring Boot 4.1.1
Spring Web
Spring Data JPA (Hibernate 7)
Spring Security (JWT)          # implemented
PostgreSQL Driver
Maven (mvnw wrapper)
REST APIs
Spring WebSocket               # planned
Spring AI (selective use)      # planned
```

---

# 🐘 Database

## PostgreSQL + PGVector

PostgreSQL is the **single** primary database — for both relational data and vector search (via the `pgvector` extension). There is no separate FAISS index; vectors live alongside relational metadata for simpler filtering and joins.

Possible tables:

```text
users
roles
refresh_tokens
destinations
monuments
trips
itineraries
itinerary_items
guides
homestays
experiences
bookings
reviews
community_posts
comments
likes
emergency_contacts
crowd_predictions
eco_scores
tourism_events
knowledge_chunks        -- text + pgvector embedding column
```

Example `pgvector` column:

```sql
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE knowledge_chunks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    content TEXT NOT NULL,
    source TEXT NOT NULL,
    embedding VECTOR(1024)   -- BGE-M3 embedding dimension
);

CREATE INDEX ON knowledge_chunks USING hnsw (embedding vector_cosine_ops);
```

> **Current state:** the backend creates its tables (`users`, `destinations`) through Hibernate (`ddl-auto=update`). Locally it reads `POSTGRES_URL` (a local PostgreSQL install or a Neon database). The deployed backend uses a **Render PostgreSQL 18** database (`yatraverse-db`, Singapore) through its *Internal Database URL*.
>
> **RAG database:** the RAG pipeline uses **Neon** (free Postgres with `pgvector`) instead of local Docker, because Docker Desktop needs BIOS virtualization, which is off on the dev machine. The `chunks` table uses `vector(1024)` with an HNSW index, and the AI service reads the connection string from the `DATABASE_URL` variable.
>
> **Planned:** Render's free Postgres is temporary, so the relational data will also move to Neon before the Render database expires. Only `POSTGRES_URL`, username and password change when switching hosts.

---

# 🔐 Authentication

Authentication is handled entirely by **Spring Security + JWT**, backed by PostgreSQL — no Firebase Auth dependency.

### Flow

```text
Signup / Login
      ↓
Spring Security AuthenticationManager
      ↓
Password verified (BCrypt)
      ↓
Access Token (JWT, short-lived)
+ Refresh Token (stored, rotated)
      ↓
Client stores tokens securely
      ↓
Subsequent requests → Authorization: Bearer <token>
      ↓
JWT Filter validates + sets SecurityContext
```

> **Status:** signup and login endpoints, `JwtUtil`, `JwtAuthFilter` and `SecurityConfig` are implemented in the backend, and login and signup now work end-to-end from the Android app against the deployed backend. Refresh tokens, roles and `@PreAuthorize` are still planned. The current token lifetime is set with `JWT_EXPIRATION` (86400000 ms = 24 hours).

### What the team learns

- BCrypt password hashing
- JWT signing/verification (HS256/RS256)
- Custom `OncePerRequestFilter` for token validation
- Role-based method security (`@PreAuthorize`)
- Refresh token rotation and revocation

---

# 📡 Real-Time Notifications

Real-time features (emergency alerts, booking updates, community activity) are delivered via **WebSocket** (Spring WebSocket / STOMP) instead of Firebase Cloud Messaging.

```text
Client connects
      ↓
WebSocket handshake (JWT-authenticated)
      ↓
STOMP subscription (per-user / topic)
      ↓
Backend publishes event
      ↓
Client receives real-time push
      ↓
Android local notification shown
```

For scenarios requiring delivery while the app is fully closed/offline, a push provider can be layered in later — this is deliberately out of scope for the MVP to keep the learning focus on building real-time infra from scratch.

---

# 🗄️ Object Storage

**MinIO** (S3-compatible) replaces Firebase Storage for:

```text
Profile Images
Travel Photos
Community Media
Heritage Scanner Images
```

Backend interacts with MinIO via the AWS S3 SDK (Java) / `boto3` (Python), so the same code patterns transfer directly to any real S3-compatible provider in production.

> **Free deployment:** MinIO is used for local development. When deployed, the same code points at **Cloudflare R2** (S3-compatible) by changing only `MINIO_ENDPOINT`, the keys and the bucket. Enable path-style access in the S3 client so both work.

---

# 🗺️ Maps

**OpenStreetMap tile data + MapLibre GL** replaces Google Maps SDK:

- Android: MapLibre GL Native
- Routing: OSRM or GraphHopper (self-hosted or public instance)
- Geocoding: Nominatim

This avoids Google Maps API billing/vendor lock-in and gives the team direct exposure to geospatial data and tile serving.

---

# 🤖 LLM Service — Qwen3 8B

## What changed from the original plan

The project originally proposed training a small tourism-domain LLM (**YatraLM**) completely from scratch. The team has decided to instead load and run an existing open-weight model, **Qwen3 8B**, via Hugging Face **Transformers/PyTorch**, and to build the RAG, prompting, and (optionally) fine-tuning layers around it.

This still delivers strong LLM-engineering learning — model loading, tokenization, quantization, inference optimization, prompt engineering, and optionally LoRA fine-tuning — without the multi-month cost of training a transformer from random weights.

## LLM Service Responsibilities

```text
Model Loading (Transformers)
      ↓
Tokenization (Qwen3 tokenizer)
      ↓
Quantization (bitsandbytes / GGUF, optional)
      ↓
Prompt Construction (system + retrieved context + user query)
      ↓
Inference (generate)
      ↓
Post-processing / Response Formatting
```

## What the team learns

- Loading and running a real pretrained transformer with `transformers`
- Tokenizer behavior, context windows, chat templates
- KV-cache, batching, and quantization trade-offs (4-bit/8-bit)
- Prompt engineering and system-prompt design for a domain assistant
- Optional: parameter-efficient fine-tuning (LoRA/QLoRA) on tourism data
- Serving inference behind a FastAPI endpoint with proper timeouts/streaming

## Example Configuration

```text
Model            = Qwen3-8B
Precision        = bf16 / 4-bit quantized (hardware dependent)
Context length    = model default (check Qwen3 docs)
Serving          = FastAPI + Transformers `generate()` (streaming optional)
Fine-tuning      = Optional LoRA adapter on tourism instruction data
```

## Optional Fine-Tuning Data

If the team chooses to fine-tune with LoRA, the same tourism dataset structure from the original plan remains useful:

```text
data/
│
├── tourism/
│   ├── destinations/
│   ├── monuments/
│   ├── culture/
│   ├── food/
│   ├── transportation/
│   └── travel_guides/
│
└── instruction/
```

---

# 🔎 RAG — Retrieval-Augmented Generation

Qwen3 8B should not be expected to know India-specific, up-to-date tourism details out of the box. YatraVerse uses **RAG** to ground answers in a curated tourism knowledge base.

# RAG Architecture

```text
Tourism Documents
       ↓
Text Extraction
       ↓
Cleaning
       ↓
Chunking
       ↓
BGE-M3 Embedding Model
       ↓
PostgreSQL + PGVector
       ↓
User Question
       ↓
Question Embedding (BGE-M3)
       ↓
Cosine Similarity Search (PGVector / HNSW index)
       ↓
Top-K Documents
       ↓
Context + Question
       ↓
Qwen3 8B
       ↓
Final Answer
```

---

# 🗂️ RAG Knowledge Base

```text
knowledge_base/
│
├── monuments/
│   ├── sanchi.txt
│   ├── khajuraho.txt
│   ├── taj_mahal.txt
│   └── konark.txt
│
├── destinations/
│   ├── madhya_pradesh.txt
│   ├── gujarat.txt
│   ├── rajasthan.txt
│   └── kerala.txt
│
├── culture/
├── food/
├── transportation/
├── travel_rules/
└── travel_guides/
```

---

# 🧪 RAG Development Strategy

We will first build RAG **without LangChain**, for learning purposes.

Implement manually:

```text
1. Load documents
2. Clean documents
3. Split documents
4. Generate BGE-M3 embeddings
5. Store embeddings in PGVector
6. Search similar vectors (cosine similarity / HNSW)
7. Retrieve Top-K documents
8. Create context
9. Pass context to Qwen3 8B
10. Generate answer
```

After understanding the complete pipeline, the same system can optionally be reimplemented using **LangChain** or **Spring AI** for comparison and industry-framework experience.

---

# 🔢 Vector Search

**PostgreSQL + PGVector is the only vector store** — there is no separate FAISS index to keep in sync, since embeddings and relational tourism metadata live in the same database and can be joined/filtered together directly in SQL.

```text
Embedding Model  = BGE-M3 (multilingual)
Storage          = PostgreSQL (pgvector extension)
Index            = HNSW (vector_cosine_ops)
```

---

# 👁️ Heritage Scanner

```text
Android Camera
      ↓
Image → MinIO
      ↓
Vision Model (PyTorch + OpenCV)
      ↓
Monument Identification
      ↓
Monument Database / RAG (PGVector)
      ↓
Qwen3 8B
      ↓
Historical Explanation
```

Example:

```text
User takes image
       ↓
"Sanchi Stupa"
       ↓
Retrieve Sanchi information (PGVector)
       ↓
Qwen3 8B
       ↓
History + Architecture + Travel Information
```

---

# 📈 Crowd Prediction

Crowd prediction is handled by a dedicated ML model — never by the LLM.

### Input

```text
Historical Visitors
Weather
Holiday
Day of Week
Season
Events
```

### Output

```text
LOW
MEDIUM
HIGH
```

Possible models:

```text
Random Forest
XGBoost
LightGBM
LSTM
```

Qwen3 8B can explain the prediction in natural language but should not replace the numerical prediction model.

---

# 🌱 Eco Travel Score

The Eco Score uses a transparent, deterministic Java rule engine — not an LLM.

Example:

```text
Transport                 30 points
Accommodation             25 points
Public Transport          20 points
Travel Behaviour          15 points
Waste Reduction           10 points
------------------------------------
Total                     100 points
```

Example:

```text
Eco Score = 82/100
```

Qwen3 8B can explain the score to the user in plain language.

---

# 📱 Android Application

## Recommended Screens

```text
Splash Screen
     ↓
Login / Signup (JWT)
     ↓
Home
     │
     ├── AI Trip Planner
     ├── Smart Itinerary
     ├── Heritage Scanner
     ├── Explore Destinations
     ├── Local Experiences
     ├── Bookings
     ├── Eco Score
     ├── Crowd Prediction
     ├── Travel Community
     ├── Emergency / SOS
     └── Profile
```

---

# 📁 Complete Project Folder Structure

```text
YatraVerse-AI/
│
├── README.md
├── PROJECT_PROGRESS.md
├── WHY_TO_USE_THIS_STACK
├── LICENSE
├── .gitignore
├── CONTRIBUTING.md
├── docker-compose.yml          # optional (needs BIOS virtualization); Neon replaces local Postgres
│
├── android-app/                          # ✅ login + signup + explore list, connected to the live backend
│   ├── app/
│   │   ├── build.gradle
│   │   └── src/main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/com/yatraverse/
│   │       │   ├── MainActivity.kt
│   │       │   ├── data/
│   │       │   │   ├── api/                # ApiService, RetrofitClient
│   │       │   │   ├── local/              # SessionManager (token storage)
│   │       │   │   ├── models/             # AuthModels, Destination
│   │       │   │   ├── repository/
│   │       │   │   └── ws/                 # WebSocket client (planned)
│   │       │   ├── domain/
│   │       │   │   ├── models/
│   │       │   │   └── usecases/
│   │       │   └── presentation/
│   │       │       ├── adapters/           # DestinationAdapter lives in screens/explore for now
│   │       │       ├── navigation/
│   │       │       ├── screens/            # splash, login, home, explore, bookings, community, profile
│   │       │       └── viewmodels/
│   │       └── res/                        # layout, menu, navigation, drawable, mipmap-anydpi-v26, values
│   ├── build.gradle
│   ├── settings.gradle
│   └── gradlew / gradlew.bat
│
├── backend/                              # ✅ Spring Boot 4.1.1, Java 25 (live on Render)
│   ├── Dockerfile                        # used by Render to build the backend
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd / .mvn/
│   └── src/main/
│       ├── java/com/yatraverse/backend/
│       │   ├── BackendApplication.java
│       │   ├── controller/               # AuthController, DestinationController
│       │   ├── service/                  # AuthService
│       │   ├── repository/               # UserRepository, DestinationRepository
│       │   ├── model/                    # User, Destination (JPA entities)
│       │   ├── dto/                      # LoginRequest, SignupRequest, AuthResponse
│       │   ├── security/                 # JwtAuthFilter, JwtUtil, SecurityConfig
│       │   ├── websocket/                # planned
│       │   └── storage/                  # planned (MinIO / R2 client)
│       └── resources/application.properties
│
├── ai-service/
│   │
│   ├── app/
│   │   ├── api/                  # FastAPI route handlers
│   │   ├── schemas/               # Pydantic request/response models
│   │   ├── models/                # DB/data models used by ai-service
│   │   ├── vision/                # Heritage Scanner (monument recognition)
│   │   │
│   │   ├── rag/                   # ✅ SINGLE source of truth for RAG
│   │   │   ├── ingestion/
│   │   │   ├── chunking/
│   │   │   ├── embeddings/         # BGE-M3
│   │   │   ├── retrieval/          # PGVector queries
│   │   │   └── pipeline.py
│   │   │
│   │   ├── llm_service/           # Qwen3 8B loading + inference
│   │   │   ├── model_loader.py
│   │   │   ├── inference.py
│   │   │   └── prompts.py
│   │   │
│   │   ├── core/                  # config, logging, startup
│   │   └── main.py
│   │
│   ├── requirements.txt
│   └── Dockerfile
│
├── llm/                            # Optional: LoRA fine-tuning on Qwen3 8B
│   │
│   ├── finetune/
│   │   ├── prepare_dataset.py
│   │   ├── lora_config.py
│   │   └── train_lora.py
│   │
│   ├── evaluate.py
│   └── checkpoints/
│
├── ml/
│   │
│   ├── crowd_prediction/
│   │   ├── train.py
│   │   ├── model.py
│   │   └── evaluate.py
│   │
│   └── eco_score/
│       ├── rules.py
│       └── calculate.py
│
├── data/
│   ├── raw/
│   ├── processed/
│   └── instruction/
│
├── knowledge_base/                 # RAG source documents
│   ├── monuments/
│   ├── destinations/
│   ├── culture/
│   ├── food/
│   ├── transportation/
│   └── travel_guides/
│
├── docs/
│   ├── architecture/
│   ├── api/
│   ├── database/
│   └── llm/
│
└── tests/
    ├── android/
    ├── backend/
    ├── ai/
    └── llm/
```

---

# 👥 Team Collaboration

For a team project, divide the project into independent modules.

## Team Member 1 — Android Developer

Responsible for:

```text
Android UI
Navigation
Login (JWT flow)
Home
Trip Planner UI
MapLibre integration
Camera
WebSocket client
Community
Profile
```

---

## Team Member 2 — Backend Developer

Responsible for:

```text
Spring Boot
Spring Security (JWT)
Spring WebSocket
MinIO integration
REST APIs
PostgreSQL
Trip APIs
Booking APIs
Review APIs
Community APIs
```

---

## Team Member 3 — LLM + RAG Engineer

Responsible for:

```text
Qwen3 8B model loading & inference
Quantization
Prompt engineering
Optional LoRA fine-tuning
BGE-M3 embeddings
RAG pipeline
PGVector integration
LLM Evaluation
```

---

## Team Member 4 — ML / AI Engineer

Responsible for:

```text
Heritage Scanner
Computer Vision
Crowd Prediction
Eco Score
Tourism Analytics
AI Integration
```

---

# 🌿 Git Branch Strategy

Use:

```text
main
  │
  └── develop
       │
       ├── feature/android-auth
       ├── feature/android-trip-planner
       ├── feature/android-community
       │
       ├── feature/backend-jwt-auth
       ├── feature/backend-websocket
       ├── feature/backend-minio
       ├── feature/backend-trip-api
       ├── feature/backend-booking
       │
       ├── feature/llm-qwen3-inference
       ├── feature/llm-lora-finetune
       ├── feature/rag-pipeline
       ├── feature/rag-pgvector
       │
       ├── feature/heritage-scanner
       ├── feature/crowd-prediction
       └── feature/eco-score
```

---

# 🔄 Git Workflow

## 1. Clone Repository

```bash
git clone https://github.com/YOUR-TEAM/YatraVerse-AI.git
cd YatraVerse-AI
```

## 2. Switch to Development Branch

```bash
git checkout develop
git pull origin develop
```

## 3. Create Feature Branch

```bash
git checkout -b feature/rag-pgvector
```

## 4. Work on Your Module

Make your changes.

## 5. Check Changes

```bash
git status
```

## 6. Add Changes

```bash
git add .
```

## 7. Commit

```bash
git commit -m "feat: add pgvector retrieval pipeline"
```

## 8. Push

```bash
git push origin feature/rag-pgvector
```

## 9. Create Pull Request

Create a Pull Request:

```text
feature/rag-pgvector
        ↓
develop
```

Another team member should review the code before merging.

---

# 📝 Commit Convention

### Feature

```text
feat: add trip planning API
```

### Bug Fix

```text
fix: resolve itinerary API error
```

### Documentation

```text
docs: update LLM service setup
```

### Testing

```text
test: add pgvector retrieval tests
```

### Refactoring

```text
refactor: improve RAG retrieval
```

---

# ⚙️ Installation

## Prerequisites

Install:

```text
Android Studio
JDK 25
Python 3.x
Git
Neon account (free managed Postgres with pgvector)
Maven
Docker (optional; Render builds the backend image itself)
```

Recommended for LLM inference:

```text
NVIDIA GPU (8B model, ideally 16GB+ VRAM for bf16; less with 4-bit quantization)
CUDA
```

---

# 📥 Clone Repository

```bash
git clone https://github.com/YOUR-TEAM/YatraVerse-AI.git
cd YatraVerse-AI
```

---

# 🐘 Set Up Neon (Postgres + pgvector)

Docker is not used for the database. Docker Desktop needs BIOS virtualization, so the RAG database lives on **Neon** (free tier).

1. Create a free project at neon.tech (pick a region close to you).
2. Open the **SQL Editor**, enable pgvector, and create the `chunks` table (`vector(1024)` column and an HNSW index, see the Database section).
3. Copy the connection string from the Neon dashboard and keep it only in your local `.env` as `DATABASE_URL`. Never commit it or paste it in chats.

```bash
cp .env.example .env        # then fill in the values (DATABASE_URL for Neon)
```

```text
Neon (Postgres + pgvector)  ->  DATABASE_URL (ai-service)
Spring Boot backend         ->  POSTGRES_URL (Render for now)
```

---

# ☕ Run Spring Boot Backend

Go to:

```bash
cd backend
```

Configuration is read from environment variables (with local defaults), so the same code runs locally and on free hosts. Configure in:

```text
src/main/resources/application.properties
```

Example:

```properties
server.port=${PORT:8080}

spring.datasource.url=${POSTGRES_URL:jdbc:postgresql://localhost:5432/yatraverse}
spring.datasource.username=${POSTGRES_USERNAME:yatraverse}
spring.datasource.password=${POSTGRES_PASSWORD:yatraverse}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# property key names below are illustrative; check the real application.properties
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}

minio.endpoint=${MINIO_ENDPOINT:http://localhost:9000}
minio.access-key=${MINIO_ACCESS_KEY:yatraverse}
minio.secret-key=${MINIO_SECRET_KEY:yatraverse123}
minio.bucket=${MINIO_BUCKET:yatraverse-media}
```

Run:

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

The project targets **JDK 25** (the Docker image uses Java 25).

Backend:

```text
http://localhost:8080
```

---

# 🗄️ Run MinIO (Object Storage)

Not needed yet (object storage is not started). Use Cloudflare R2 when deployed. For local testing, run MinIO as a standalone binary, or with Docker if BIOS virtualization is enabled:

```bash
docker run -p 9000:9000 -p 9001:9001 \
  -e "MINIO_ROOT_USER=YOUR_MINIO_ACCESS_KEY" \
  -e "MINIO_ROOT_PASSWORD=YOUR_MINIO_SECRET_KEY" \
  minio/minio server /data --console-address ":9001"
```

Console: `http://localhost:9001`

---

# 🐘 Enable PGVector

Run this in the Neon SQL Editor:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

---

# 🐍 Run AI Service

Go to:

```bash
cd ai-service
```

### Windows

```bash
python -m venv venv
venv\Scripts\activate
```

### Linux / macOS

```bash
python3 -m venv venv
source venv/bin/activate
```

Install dependencies:

```bash
pip install -r requirements.txt
```

Run:

```bash
uvicorn app.main:app --reload --port 8000
```

---

# 🤖 Load & Serve Qwen3 8B

Go to:

```bash
cd ai-service/app/llm_service
```

Load the model (example):

```python
from transformers import AutoModelForCausalLM, AutoTokenizer

model_name = "Qwen/Qwen3-8B"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForCausalLM.from_pretrained(
    model_name,
    torch_dtype="auto",
    device_map="auto",
)
```

For lower VRAM usage, load with 4-bit quantization via `bitsandbytes`.

Optional LoRA fine-tuning:

```bash
cd llm/finetune
python prepare_dataset.py
python train_lora.py
```

---

# 🔎 Run RAG

Prepare documents:

```bash
python app/rag/ingestion/load_documents.py
```

Generate BGE-M3 embeddings and store in PGVector:

```bash
python app/rag/embeddings/create_embeddings.py
```

Test retrieval:

```bash
python app/rag/retrieval/test_retrieval.py
```

---

# 📱 Run Android Application

1. Open **Android Studio**
2. Open the `android-app/` directory
3. Allow Gradle synchronization
4. Configure MapLibre style/API endpoint
5. Configure the backend URL and WebSocket URL
6. Start an Android emulator or connect a physical device
7. Click **Run**

The app currently points at the deployed Render backend (`https://yatraverse-bnv5.onrender.com`). The first request after idle can take about a minute while the free instance wakes up.

For an Android Emulator, a **local** Spring Boot server usually uses:

```text
http://10.0.2.2:8080
ws://10.0.2.2:8080/ws
```

instead of:

```text
http://localhost:8080
```

To use the deployed backend instead, set the Retrofit base URL in `RetrofitClient.kt` to:

```text
https://yatraverse-bnv5.onrender.com/
```

The first request after idle can take up to a minute because the free instance sleeps.

---

# 🔐 Environment Variables

Never commit credentials to GitHub.

Example:

```text
POSTGRES_URL=
POSTGRES_USERNAME=
POSTGRES_PASSWORD=

DATABASE_URL=                  # Neon connection string for ai-service (never commit)

JWT_SECRET=
JWT_EXPIRATION=86400000        # token lifetime in ms (24 h)

MINIO_ENDPOINT=
MINIO_ACCESS_KEY=
MINIO_SECRET_KEY=
MINIO_BUCKET=

HUGGINGFACE_TOKEN=          # if required for gated model access

OSRM_ROUTING_URL=
NOMINATIM_URL=

AI_SERVICE_URL=http://localhost:8000    # where the backend reaches the FastAPI service
CORS_ALLOWED_ORIGINS=                   # set when deployed
```

> The `MINIO_*` variables hold values for **any S3-compatible storage** (MinIO locally, Cloudflare R2 when deployed).
> For Neon, `POSTGRES_URL` must end with `?sslmode=require`. The AI service uses the single variable `DATABASE_URL` for Neon.

Sensitive files should not be committed:

```text
.env
service-account.json
API keys
private keys
database passwords
```

Add them to `.gitignore`.

---

# 💸 Free Deployment

The stack does not change. Only **where each piece runs** changes, and this is done purely through environment variables.

| Piece | Local development | Free deployment | Status |
|---|---|---|---|
| PostgreSQL | Neon (or a local Postgres install) | **Render PostgreSQL** now (temporary) for the backend; **Neon** for RAG (pgvector) | ✅ Render DB `yatraverse-db` live; 🚧 Neon for RAG |
| Vector DB (pgvector) | Neon | Neon free tier | ⏳ planned |
| Object storage | MinIO (standalone, optional) | **Cloudflare R2** (S3-compatible) | ⏳ not started |
| Spring Boot backend | `./mvnw spring-boot:run` | **Render** free web service (Docker) | ✅ live |
| Android app | Emulator / phone | APK shared via **GitHub Releases** | 🚧 app talks to the live backend; APK release not started |
| AI service (FastAPI + Qwen3 + BGE-M3) | Your machine (RTX 4050 6 GB) | Cloudflare Tunnel (see below) | ⏳ not started |

## Live backend

```text
https://yatraverse-bnv5.onrender.com
```

Free instances sleep after idle time. The first cold start took about 107 seconds to boot Spring, so expect a slow first request.

## Render web service settings (working configuration)

| Field | Value |
|---|---|
| Language | Docker |
| Branch | `main` |
| Region | Singapore (same region as the database) |
| Root Directory | *empty* |
| Dockerfile Path | `backend/Dockerfile` |
| Docker Build Context Directory | `backend` |
| Docker Command | *empty* |
| Instance type | Free |

## Render environment variables

| Key | Value |
|---|---|
| `POSTGRES_URL` | JDBC form of the database's **Internal** URL: `jdbc:postgresql://<internal-host>/<database>` (no username or password inside) |
| `POSTGRES_USERNAME` | database user from the Render database page |
| `POSTGRES_PASSWORD` | database password from the Render database page |
| `JWT_SECRET` | long random string (Render's **Generate** button works) |
| `JWT_EXPIRATION` | `86400000` |

Render sets `PORT` itself (the app listened on 10000), and the backend reads it through `server.port=${PORT:8080}`. Never commit these values or paste them in chats or screenshots.

## Redeploying and common problems

- Render deploys from the branch chosen in the service settings (`main`). Merge and push to `main` to update the deployed backend.
- **"No matching entries" in the Dockerfile Path field** means the file does not exist on the selected branch. Push or merge it into that branch, refresh the page, and select it from the dropdown.
- Use the database's **Internal** URL for the backend on Render. It only works between Render services in the same region.
- If the backend fails to start, open the service's **Logs** tab first.

### Notes and limits

- The free backend sleeps after idle time. JVM memory is capped with `JAVA_TOOL_OPTIONS=-Xmx350m` on Render because the free plan has 512 MB RAM. Auto-Deploy is set to On Commit.
- RAG database: Neon (free, pgvector) instead of local Docker, because Docker Desktop needs BIOS virtualization. No paid AI APIs are used: BGE-M3 and Qwen3 run locally (Ollama). Hardware: RTX 4050 6 GB, so Qwen3 4B is the safe default and 8B is tried at 4-bit.
- Render's free Postgres is temporary. Check its expiry date in the Render dashboard and move to **Neon** before then (dump and restore, then change the three `POSTGRES_*` variables; for Neon, the URL needs `?sslmode=require`).
- Free-tier limits change often. **Verify current limits before relying on them.**

### Destination image endpoint

Authenticated `PUT /api/destinations/{id}` with body `{ "imageUrl": "..." }` updates a destination's image. The nine seeded destinations use freely licensed Wikimedia Commons images (960px thumbnails from the Wikipedia REST summary API). Send a `User-Agent` header when calling Wikipedia.

### Self-hosting option (old laptop)

Because every host-specific value comes from environment variables and the backend ships as a Docker image, the backend can also run on your own hardware instead of Render. An 8 GB RAM laptop is enough for Spring Boot, Postgres (with pgvector) and MinIO; it is **not** enough for Qwen3 8B.

1. Install Docker Desktop (WSL2) on Windows, or Ubuntu Server for better uptime. Docker Desktop needs BIOS virtualization enabled (it is off on the current dev machine, which is why Neon is the current plan).
2. Clone the repo and create a `.env` with new secrets (never commit it).
3. Add the backend service to `docker-compose.yml` (it builds from `backend/Dockerfile`) and run `docker compose up -d --build`.
4. Expose it with a Cloudflare Tunnel (`cloudflared tunnel --url http://localhost:8080`). Quick tunnels change URL on every restart; a named tunnel needs a free Cloudflare account and a domain.
5. Update `BASE_URL` in `RetrofitClient.kt` and rebuild the app.
6. Stop the laptop sleeping (power settings), cap Docker memory in `.wslconfig`, and schedule `pg_dump` backups.

Trade-offs: no cold starts or expiring database, but you own uptime, backups and security. Render can remain as a fallback.

### The AI service

Qwen3 8B and BGE-M3 are too large for any free web container. Options that keep the same models:

1. Run the AI service on your own machine (4-bit quantized) and expose it to the deployed backend through a free tunnel. It is online only while your machine is on, which is fine for demos and viva.
2. Run it on a free notebook GPU (Colab / Kaggle) during demo sessions.

The backend must treat the AI service as optional: if `AI_SERVICE_URL` is unreachable, AI endpoints return a clear "AI service offline" response and the rest of the app keeps working.

---

# 🧪 Testing

## Android

```text
Unit Tests
UI Tests
Navigation Tests
API Tests
WebSocket Tests
```

## Spring Boot

```text
Controller Tests
Service Tests
Repository Tests
Security/JWT Filter Tests
Integration Tests
```

## LLM Service

Test:

```text
Model loading
Tokenization
Prompt construction
Inference output shape/latency
```

## RAG

Evaluate:

```text
Retrieval Relevance
Context Relevance
Answer Correctness
Faithfulness
Hallucination
Latency
```

## ML

For crowd prediction:

```text
Accuracy
Precision
Recall
F1 Score
```

For numerical prediction:

```text
MAE
RMSE
```

---

# 🛡️ Security

YatraVerse implements:

- JWT-based authentication and authorization (Spring Security)
- Input validation
- BCrypt password hashing
- HTTPS in production
- Database access control
- API rate limiting
- MinIO bucket policies / signed URLs for secure file upload
- WebSocket connection authentication (JWT on handshake)
- No credentials in GitHub
- Secrets live only in Render environment variables or a local `.env`; any credential that was pasted into a chat or screenshot must be rotated

Emergency functionality does not depend on an AI-generated response.

---

# 🚧 Development Roadmap

## Phase 1 — Project Setup  ✅ mostly done (AI environment still pending)

```text
Repository
Android Project
Spring Boot
PostgreSQL + PGVector
MinIO
Python AI Environment
```

## Phase 2 — Auth & Basic Application  🚧 in progress (signup + login work end-to-end; Explore shows real images; Home/Profile content still basic)

```text
Spring Security + JWT
Signup / Login
Home
Profile
Navigation
```

## Phase 3 — Backend  🚧 started (Destination API: list, detail (404 handled), create, image update)

```text
User API
Destination API
Trip API
Itinerary API
Booking API
Review API
```

## Phase 4 — LLM Service

```text
Load Qwen3 8B (Transformers)
     ↓
Tokenization / Prompting
     ↓
Inference Endpoint (FastAPI)
     ↓
Optional: LoRA Fine-tuning
```

## Phase 5 — RAG  🚧 started

🚧 working locally: knowledge_base/monuments (9 files), ai-service/app/rag ingest.py, retrieve.py and answer.py, Neon chunks table, FastAPI /chat, Android chat screen. Efficiency choices: section-aware chunking, destination filter, HNSW index, only the best 4-6 chunks sent to the LLM, "I don't know" threshold. LLM: Qwen3 4B via Ollama (RTX 4050, 6 GB VRAM).

```text
Tourism Documents
     ↓
Chunking
     ↓
BGE-M3 Embeddings
     ↓
PGVector
     ↓
Retriever
     ↓
Qwen3 8B
```

## Phase 6 — AI Features

```text
Heritage Scanner
Crowd Prediction
Eco Score
AI Trip Planner
Smart Itinerary
```

## Phase 7 — Real-Time & Storage

```text
WebSocket notifications
MinIO media pipeline
Emergency broadcast
```

## Phase 8 — Community

```text
Posts
Comments
Likes
Reviews
Photos
```

## Phase 9 — Marketplace

```text
Guides
Homestays
Artisans
Experiences
Bookings
```

## Phase 10 — Analytics

```text
Visitor Trends
Destination Popularity
Booking Analytics
Feedback
Crowd Analytics
```

## Phase 11 — Deployment  🚧 started (backend + database live on Render)

```text
Local dev (Spring Boot, FastAPI, local models)
     ↓
Spring Boot -> Render (free)
     ↓
PostgreSQL + PGVector -> Neon (free)
     ↓
MinIO -> Cloudflare R2 (free)
     ↓
AI Service (Qwen3 8B) -> laptop + tunnel / free notebook GPU (demo)
     ↓
Android -> APK via GitHub Releases
```

---

# 🏆 Milestones

- [x] JWT signup/login implemented in Spring Boot (login verified locally)
- [x] JWT auth working end-to-end (Android app → deployed backend): signup and login
- [x] Explore screen shows real destination images (Wikimedia URLs saved through the backend PUT endpoint)
- [x] Destination detail screen
- [x] 9 destinations with images
- [x] Login loader for slow server start
- [x] RAG: knowledge base (9 monuments) ingested into pgvector, retrieval tested
- [x] PGVector extension enabled and indexed (on Neon)
- [x] Qwen3 runs inference locally (4B via Ollama on an RTX 4050; 8B Transformers still planned)
- [x] RAG retrieval returns relevant chunks
- [x] RAG + Qwen3 produces grounded answers
- [x] FastAPI /chat, Spring Boot /api/chat and the Android chat screen work end to end locally
- [ ] Optional LoRA fine-tune improves domain responses
- [ ] MinIO upload/download working
- [ ] WebSocket real-time notifications working
- [ ] Heritage Scanner end-to-end
- [ ] Crowd prediction model trained
- [ ] Eco score rule engine complete
- [ ] Android integration complete
- [x] Backend deployed on Render with a Render PostgreSQL database
- [x] Render database password rotated
- [ ] Refresh tokens and roles
- [ ] Free deployment complete (Neon + R2 + AI service)

---

# 🔮 Future Scope

Future versions of YatraVerse AI can include:

```text
Multilingual RAG (Hindi/regional languages via BGE-M3)
        ↓
Voice-Based Travel Assistant
        ↓
Speech-to-Text
        ↓
Text-to-Speech
        ↓
AI Travel Agent
        ↓
Tool Calling (Qwen3 function calling)
        ↓
Real-Time Itinerary Adaptation
        ↓
Personalized Recommendations
        ↓
On-Device / Quantized Local Inference
```

Other possibilities:

- Hindi and regional-language tourism support
- Voice-based trip planning
- Offline tourism assistant (quantized model on-device)
- AI travel agent with tool calling
- AR heritage exploration
- Real-time route optimization (self-hosted OSRM)
- Advanced crowd forecasting
- Carbon emission estimation
- Personalized recommendation engine
- Local business recommendation
- Tourism demand forecasting
- Optional managed push notifications layered on top of WebSocket

---

# 📊 Final Architecture

```text
                    YATRAVERSE AI
                          │
          ┌───────────────┼────────────────┐
          │               │                │
          ▼               ▼                ▼
       Android        Spring Boot       AI Service
          │           (Security/WS)     (FastAPI)
          │               │                │
          │               ▼                │
          │       PostgreSQL + PGVector     │
          │               │                │
          │               ▼                │
          │            MinIO                │
          │                                │
          │               ┌────────────────┤
          │               │                │
          │               ▼                ▼
          │           Qwen3 8B             ML
          │               │                │
          │               ▼                ├── Crowd
          │              RAG                └── Eco
          │               │
          │               ▼
          │       Tourism Knowledge
          │
          └──────── OpenStreetMap / MapLibre
```

---

# 🎓 Learning Objective

The most important goal of this project is to understand the complete modern AI-integrated application pipeline:

```text
DATA
 ↓
CHUNKING & EMBEDDINGS (BGE-M3)
 ↓
VECTOR STORAGE (PGVector)
 ↓
RETRIEVAL (RAG)
 ↓
LLM INFERENCE (Qwen3 8B / Transformers)
 ↓
AI SERVICE (FastAPI)
 ↓
SECURE BACKEND (Spring Boot + JWT + WebSocket)
 ↓
RELATIONAL DATABASE (PostgreSQL)
 ↓
OBJECT STORAGE (MinIO)
 ↓
ANDROID APPLICATION
```

This allows the team to gain practical experience in:

- Android Development
- Backend Development & Security
- REST + WebSocket API Development
- Database Engineering (relational + vector)
- Machine Learning
- Computer Vision
- Natural Language Processing
- Pretrained LLM Loading & Inference
- Prompt Engineering
- Optional LoRA Fine-Tuning
- RAG
- Vector Databases
- Object Storage
- AI Evaluation
- Model Deployment
- Git/GitHub Collaboration

---

# 📌 Project Status

```text
🚧 Under Development

Done:   repo scaffolding, Android app (splash, login, signup, home, explore,
        destination detail, chat, bookings, community, profile screens +
        Retrofit + session storage), Spring Boot backend (JWT auth,
        destinations API, /api/chat), Dockerfile, backend + PostgreSQL
        deployed on Render (free tier), 9 destinations with images,
        RAG pipeline: knowledge base (9 monuments) in Neon pgvector,
        BGE-M3 retrieval with an "I don't know" threshold, FastAPI /chat,
        grounded answers from Qwen3 4B via Ollama (local),
        Android chat screen working end to end on the emulator
Next:   deploy the AI service, refresh tokens and roles, move app data to
        Neon, trip planner, admin analytics, Heritage Scanner and the
        other AI features
```

YatraVerse AI is being developed as a collaborative academic and learning project focused on building an end-to-end smart tourism ecosystem using Android, Spring Boot (Security + WebSocket), PostgreSQL + PGVector, MinIO, OpenStreetMap/MapLibre, Machine Learning, Computer Vision, Qwen3 8B, and Retrieval-Augmented Generation.

---

# ⭐ Final Goal

> **YatraVerse AI aims to become an intelligent, personalized, safe, sustainable, and culturally aware tourism platform while providing the development team with hands-on experience in modern software engineering and applied LLM/RAG engineering — built on a self-hosted, vendor-independent stack.**