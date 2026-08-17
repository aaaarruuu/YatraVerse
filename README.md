# 🌍 YatraVerse AI

> **AI-Powered Smart Tourism Ecosystem**

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
- [Development Roadmap](#-development-roadmap)
- [Testing](#-testing)
- [Security](#-security)
- [Future Scope](#-future-scope)
- [Project Status](#-project-status)

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
Java
Spring Boot
Spring Web
Spring Data JPA
Spring Security (JWT)
Spring WebSocket
PostgreSQL Driver
Maven
REST APIs
Spring AI (selective use)
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
├── LICENSE
├── .gitignore
├── CONTRIBUTING.md
├── docker-compose.yml          # postgres+pgvector, minio, backend, ai-service
│
├── android-app/
│   │
│   ├── app/
│   │   └── src/
│   │       └── main/
│   │           ├── java/
│   │           │   └── com/yatraverse/
│   │           │       ├── ui/
│   │           │       ├── screens/
│   │           │       ├── adapters/
│   │           │       ├── viewmodels/
│   │           │       ├── repository/
│   │           │       ├── api/
│   │           │       ├── ws/                 # WebSocket client
│   │           │       └── models/
│   │           │
│   │           ├── res/
│   │           │   ├── layout/
│   │           │   ├── drawable/
│   │           │   ├── mipmap/
│   │           │   ├── values/
│   │           │   └── navigation/
│   │           │
│   │           └── AndroidManifest.xml
│   │
│   ├── build.gradle
│   └── settings.gradle
│
├── backend/
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/yatraverse/backend/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── entity/
│   │   │   │       ├── dto/
│   │   │   │       ├── config/
│   │   │   │       ├── security/               # JWT filters, providers
│   │   │   │       ├── websocket/               # STOMP config, handlers
│   │   │   │       └── storage/                 # MinIO client wrapper
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
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
JDK
Python 3.x
Git
PostgreSQL (with pgvector extension)
Maven
Docker (for MinIO)
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

# ☕ Run Spring Boot Backend

Go to:

```bash
cd backend
```

Configure PostgreSQL in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yatraverse
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_JWT_SECRET
jwt.access-token-expiry-ms=900000
jwt.refresh-token-expiry-ms=604800000

minio.endpoint=http://localhost:9000
minio.access-key=YOUR_MINIO_ACCESS_KEY
minio.secret-key=YOUR_MINIO_SECRET_KEY
minio.bucket=yatraverse-media
```

Run:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

---

# 🗄️ Run MinIO (Object Storage)

```bash
docker run -p 9000:9000 -p 9001:9001 \
  -e "MINIO_ROOT_USER=YOUR_MINIO_ACCESS_KEY" \
  -e "MINIO_ROOT_PASSWORD=YOUR_MINIO_SECRET_KEY" \
  minio/minio server /data --console-address ":9001"
```

Console: `http://localhost:9001`

---

# 🐘 Enable PGVector

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

For an Android Emulator, a local Spring Boot server usually uses:

```text
http://10.0.2.2:8080
ws://10.0.2.2:8080/ws
```

instead of:

```text
http://localhost:8080
```

---

# 🔐 Environment Variables

Never commit credentials to GitHub.

Example:

```text
POSTGRES_URL=
POSTGRES_USERNAME=
POSTGRES_PASSWORD=

JWT_SECRET=
JWT_ACCESS_EXPIRY_MS=
JWT_REFRESH_EXPIRY_MS=

MINIO_ENDPOINT=
MINIO_ACCESS_KEY=
MINIO_SECRET_KEY=
MINIO_BUCKET=

HUGGINGFACE_TOKEN=          # if required for gated model access

OSRM_ROUTING_URL=
NOMINATIM_URL=
```

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

Emergency functionality does not depend on an AI-generated response.

---

# 🚧 Development Roadmap

## Phase 1 — Project Setup

```text
Repository
Android Project
Spring Boot
PostgreSQL + PGVector
MinIO
Python AI Environment
```

## Phase 2 — Auth & Basic Application

```text
Spring Security + JWT
Signup / Login
Home
Profile
Navigation
```

## Phase 3 — Backend

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

## Phase 5 — RAG

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

## Phase 11 — Deployment

```text
Docker
     ↓
AI Service (Qwen3 8B)
     ↓
Spring Boot
     ↓
PostgreSQL + PGVector
     ↓
MinIO
     ↓
Android
```

---

# 🏆 Milestones

- [ ] JWT auth working end-to-end
- [ ] PGVector extension enabled and indexed
- [ ] Qwen3 8B loads and runs inference locally
- [ ] RAG retrieval returns relevant chunks
- [ ] RAG + Qwen3 8B produces grounded answers
- [ ] Optional LoRA fine-tune improves domain responses
- [ ] MinIO upload/download working
- [ ] WebSocket real-time notifications working
- [ ] Heritage Scanner end-to-end
- [ ] Crowd prediction model trained
- [ ] Eco score rule engine complete
- [ ] Android integration complete

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
```

YatraVerse AI is being developed as a collaborative academic and learning project focused on building an end-to-end smart tourism ecosystem using Android, Spring Boot (Security + WebSocket), PostgreSQL + PGVector, MinIO, OpenStreetMap/MapLibre, Machine Learning, Computer Vision, Qwen3 8B, and Retrieval-Augmented Generation.

---

# ⭐ Final Goal

> **YatraVerse AI aims to become an intelligent, personalized, safe, sustainable, and culturally aware tourism platform while providing the development team with hands-on experience in modern software engineering and applied LLM/RAG engineering — built on a self-hosted, vendor-independent stack.**
