# 🌍 YatraVerse AI

> **AI-Powered Smart Tourism Ecosystem**

YatraVerse AI is an AI-powered smart tourism ecosystem that combines **personalized trip planning, intelligent itinerary generation, heritage exploration, tourist safety, sustainable travel, local experiences, community interaction, and tourism analytics** into a single Android application.

The project is also designed as a **learning-focused AI engineering project**, where the team builds a small tourism-domain **Language Model (YatraLM) from scratch**, followed by **RAG (Retrieval-Augmented Generation)**, embeddings, vector search, and AI/ML services.

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Project Objectives](#-project-objectives)
- [Key Features](#-key-features)
- [System Architecture](#-system-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [YatraLM - Our Own LLM](#-yatralm---our-own-llm)
- [LLM Development Roadmap](#-llm-development-roadmap)
- [RAG Architecture](#-rag-architecture)
- [AI/ML Components](#-aiml-components)
- [Android Application](#-android-application)
- [Backend Architecture](#-backend-architecture)
- [Database](#-database)
- [Firebase](#-firebase)
- [Team Collaboration](#-team-collaboration)
- [Git Workflow](#-git-workflow)
- [Installation](#-installation)
- [Running the Project](#-running-the-project)
- [Environment Variables](#-environment-variables)
- [Development Roadmap](#-development-roadmap)
- [Testing](#-testing)
- [Security](#-security)
- [Future Scope](#-future-scope)
- [Contributors](#-contributors)
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
PostgreSQL
        +
Firebase
        +
Machine Learning
        +
Computer Vision
        +
LLM
        +
RAG
        +
Vector Search
```

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
Spring Boot API
        ↓
AI Service
        ↓
YatraLM + RAG
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
Maps / Routing
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
Image
      ↓
Vision Model
      ↓
Monument Identification
      ↓
Tourism Knowledge Base
      ↓
YatraLM + RAG
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
- Emergency notifications

### Architecture

```text
User
 ↓
SOS
 ↓
GPS Location
 ↓
Emergency Module
 ↓
Firebase / Backend
 ↓
Emergency Contact
```

> Emergency functionality should use deterministic application logic and verified emergency information rather than relying on an LLM.

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

The score is calculated using defined rules rather than asking an LLM to perform the numerical calculation.

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

The LLM can explain the prediction, but the numerical prediction is handled by a dedicated ML model.

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

Firebase Storage can be used for media files.

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
| User Authentication | Firebase / Spring Security |
| Trip Planning | YatraLM + RAG |
| Smart Itinerary | Spring Boot + AI |
| Heritage Scanner | Computer Vision |
| Tourism Knowledge | RAG |
| Crowd Prediction | ML |
| Eco Score | Rule-based scoring |
| Maps | Google Maps / OpenStreetMap |
| Notifications | Firebase FCM |
| Image Storage | Firebase Storage |
| Main Database | PostgreSQL |
| Vector Search | FAISS / pgvector |
| Mobile Application | Android Studio |
| Backend | Spring Boot |
| AI Service | Python |
| LLM | PyTorch |

---

# 🏗️ System Architecture

```text
                         YATRAVERSE AI
                              │
                              ▼
                  ┌─────────────────────┐
                  │   Android Mobile    │
                  │   Kotlin / Java     │
                  │   Android Studio    │
                  └──────────┬──────────┘
                             │
                         REST / JSON
                             │
                             ▼
                  ┌─────────────────────┐
                  │    Spring Boot      │
                  │    REST Backend     │
                  └──────────┬──────────┘
                             │
            ┌────────────────┼────────────────┐
            │                │                │
            ▼                ▼                ▼
     ┌────────────┐   ┌────────────┐   ┌─────────────┐
     │ PostgreSQL │   │  Firebase  │   │ AI Service  │
     │            │   │            │   │   Python    │
     └────────────┘   └────────────┘   └──────┬──────┘
                                              │
                           ┌──────────────────┼──────────────────┐
                           │                  │                  │
                           ▼                  ▼                  ▼
                     ┌───────────┐     ┌────────────┐     ┌───────────┐
                     │ YatraLM   │     │    RAG     │     │ ML Models │
                     │           │     │ Retrieval  │     │           │
                     │ From      │     │ + Vector   │     │ Crowd     │
                     │ Scratch   │     │ Search     │     │ Eco Score │
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
- Java
- Android SDK
- XML / Jetpack Compose
- Retrofit
- ViewModel
- Repository Pattern
- Navigation Component
- Google Maps SDK / OpenStreetMap
- Firebase Authentication
- Firebase Cloud Messaging
- Firebase Storage
- Android Location Services
- Android Camera APIs

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

Spring Boot is responsible for the main business logic and REST APIs.

### Responsibilities

- Authentication
- User management
- Trip management
- Itinerary management
- Destination APIs
- Monument APIs
- Marketplace
- Booking
- Reviews
- Community
- Emergency services
- Analytics
- Communication with AI service

### Technologies

```text
Java
Spring Boot
Spring Web
Spring Data JPA
Spring Security
PostgreSQL
Maven
REST APIs
```

---

# 🐘 Database

## PostgreSQL

PostgreSQL is the primary relational database.

Possible tables:

```text
users
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
```

For vector search, the project can initially use FAISS and later migrate to:

```text
PostgreSQL + pgvector
```

---

# 🔥 Firebase

Firebase is used for supporting application services.

### Firebase Authentication

```text
Login
Signup
Google Authentication
User Authentication
```

### Firebase Cloud Messaging

```text
Emergency notifications
Booking notifications
Travel notifications
Application notifications
```

### Firebase Storage

```text
Profile Images
Travel Photos
Community Media
Heritage Images
```

PostgreSQL remains the primary relational database.

---

# 🤖 YatraLM — Our Own LLM

## What is YatraLM?

**YatraLM** is a small tourism-domain language model developed by the team from scratch using **Python and PyTorch**.

The purpose is not to compete with GPT/Gemini-scale models.

The purpose is to understand:

```text
How an LLM actually works
```

and implement the fundamental components ourselves.

---

# 🧠 YatraLM Architecture

```text
Tourism Dataset
       ↓
Tokenizer
       ↓
Token IDs
       ↓
Token Embeddings
       ↓
Positional Embeddings
       ↓
Transformer Blocks
       ↓
Self Attention
       ↓
Multi-Head Attention
       ↓
Feed Forward Network
       ↓
Layer Normalization
       ↓
Residual Connections
       ↓
Language Model Head
       ↓
Next Token Prediction
```

---

# 📚 YatraLM Development Roadmap

## Phase 1 — Tokenizer

First build a simple tokenizer.

Example:

```text
"Khajuraho has beautiful temples"

        ↓

["Khajuraho", "has", "beautiful", "temples"]

        ↓

[152, 43, 891, 621]
```

Learn:

- Vocabulary
- Tokens
- Token IDs
- Special tokens
- Context length

Later implement BPE/subword tokenization.

---

## Phase 2 — Embeddings

Implement:

```text
Token Embedding
+
Positional Embedding
```

Example:

```text
Token
 ↓
Embedding
 ↓
Vector
```

The Transformer operates on numerical vectors.

---

## Phase 3 — Self Attention

Implement self-attention from scratch.

```text
Input
 │
 ├──── Query
 ├──── Key
 └──── Value
       │
       ▼
      Q × Kᵀ
       │
       ▼
     Softmax
       │
       ▼
Attention Weights
       │
       ▼
Weighted Values
```

Core equation:

```text
Attention(Q,K,V)
=
softmax(QKᵀ / √dk)V
```

The team should understand this mathematically before using high-level Transformer libraries.

---

## Phase 4 — Multi-Head Attention

Instead of using one attention mechanism:

```text
Head 1
Head 2
Head 3
...
Head N
```

are used simultaneously.

Then:

```text
Multiple Heads
      ↓
Concatenate
      ↓
Linear Projection
```

---

## Phase 5 — Transformer Block

Build:

```text
Input
 ↓
Multi-Head Attention
 ↓
Residual Connection
 ↓
LayerNorm
 ↓
Feed Forward Network
 ↓
Residual Connection
 ↓
LayerNorm
 ↓
Output
```

---

## Phase 6 — Build YatraLM

Combine everything:

```text
Tokenizer
    ↓
Embedding
    ↓
Transformer Blocks
    ↓
LayerNorm
    ↓
Linear Layer
    ↓
Vocabulary Probabilities
```

The model predicts:

```text
"What token should come next?"
```

Example:

```text
Input:

"The Taj Mahal is located in"

Prediction:

"Agra"
```

---

# 📊 Training YatraLM

Create a tourism-specific dataset.

Recommended data:

```text
Indian destinations
Historical monuments
Architecture
Tourism information
Travel guides
Indian culture
Food
Transportation
Local experiences
Itinerary examples
Tourism conversations
```

Dataset structure:

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

# ⚠️ Important LLM Scope

The team should start with a **very small model**.

Example starting configuration:

```text
Vocabulary       = 10,000–20,000
Embedding size   = 128–256
Layers           = 4–6
Attention heads  = 4–8
Context length   = 256–512
```

The exact configuration can change depending on available hardware.

The objective is:

> **Build, train, debug and understand the complete pipeline.**

Not:

> **Build a GPT-scale commercial model.**

---

# 🔎 RAG — Retrieval-Augmented Generation

YatraLM should not be expected to memorize all tourism information.

Instead, YatraVerse uses **RAG**.

RAG allows the model to retrieve relevant information from a tourism knowledge base before generating an answer.

---

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
Embedding Model
       ↓
Vector Database
       ↓
User Question
       ↓
Question Embedding
       ↓
Similarity Search
       ↓
Top-K Documents
       ↓
Context + Question
       ↓
YatraLM
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

We will first build RAG **without LangChain**.

This is important for learning.

Implement manually:

```text
1. Load documents
2. Clean documents
3. Split documents
4. Generate embeddings
5. Store embeddings
6. Search similar vectors
7. Retrieve Top-K documents
8. Create context
9. Pass context to YatraLM
10. Generate answer
```

After understanding the complete pipeline, implement the same system using:

```text
LangChain
```

This gives the team both:

- Core understanding
- Industry framework experience

---

# 🔢 Vector Search

### Initial implementation

Use:

```text
Hugging Face Embedding Model
+
FAISS
```

### Production-oriented implementation

Use:

```text
PostgreSQL
+
pgvector
```

This allows tourism information and vector representations to be managed within the same database ecosystem.

---

# 👁️ Heritage Scanner

```text
Android Camera
      ↓
Image
      ↓
Vision Model
      ↓
Monument Identification
      ↓
Monument Database / RAG
      ↓
YatraLM
      ↓
Historical Explanation
```

Example:

```text
User takes image
       ↓
"Sanchi Stupa"
       ↓
Retrieve Sanchi information
       ↓
YatraLM
       ↓
History + Architecture + Travel Information
```

---

# 📈 Crowd Prediction

Crowd prediction is handled by a dedicated ML model.

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

The LLM can explain the prediction but should not replace the numerical prediction model.

---

# 🌱 Eco Travel Score

The Eco Score uses a transparent scoring system.

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

The LLM can explain the score to the user.

---

# 📱 Android Application

## Recommended Screens

```text
Splash Screen
     ↓
Login / Signup
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
├── docker-compose.yml
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
│   │   │   │       └── security/
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
│   │   │   ├── embeddings/
│   │   │   ├── retrieval/
│   │   │   ├── vector_store/
│   │   │   └── pipeline.py
│   │   │
│   │   ├── llm_service/           # wraps YatraLM for inference calls
│   │   ├── core/                  # config, logging, startup
│   │   └── main.py
│   │
│   ├── requirements.txt
│   └── Dockerfile
│
├── llm/                            # YatraLM — built from scratch
│   │
│   ├── tokenizer/
│   │   ├── tokenizer.py
│   │   └── vocab.json
│   │
│   ├── model/
│   │   ├── embeddings.py
│   │   ├── attention.py
│   │   ├── transformer.py
│   │   ├── model.py
│   │   └── config.py
│   │
│   ├── dataset/
│   │   ├── prepare.py
│   │   └── dataset.py
│   │
│   ├── train.py
│   ├── generate.py
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
Login
Home
Trip Planner UI
Maps
Camera
Community
Profile
```

---

## Team Member 2 — Backend Developer

Responsible for:

```text
Spring Boot
REST APIs
PostgreSQL
Authentication
Trip APIs
Booking APIs
Review APIs
Community APIs
```

---

## Team Member 3 — LLM + RAG Engineer

Responsible for:

```text
Tokenizer
Embeddings
Self Attention
Transformer
YatraLM
Training
Generation
RAG
FAISS
pgvector
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
       ├── feature/backend-user-api
       ├── feature/backend-trip-api
       ├── feature/backend-booking
       │
       ├── feature/llm-tokenizer
       ├── feature/llm-attention
       ├── feature/llm-transformer
       ├── feature/llm-training
       ├── feature/rag-pipeline
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
git checkout -b feature/llm-tokenizer
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
git commit -m "feat: add tourism tokenizer"
```

## 8. Push

```bash
git push origin feature/llm-tokenizer
```

## 9. Create Pull Request

Create a Pull Request:

```text
feature/llm-tokenizer
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
docs: update YatraLM setup
```

### Testing

```text
test: add tokenizer tests
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
PostgreSQL
Maven
```

Optional for AI training:

```text
NVIDIA GPU
CUDA
Docker
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
python app/main.py
```

---

# 🤖 Train YatraLM

Go to:

```bash
cd llm
```

Prepare dataset:

```bash
python dataset/prepare.py
```

Train:

```bash
python train.py
```

Generate:

```bash
python generate.py
```

Evaluate:

```bash
python evaluate.py
```

---

# 🔎 Run RAG

Prepare documents:

```bash
python rag/ingestion/load_documents.py
```

Generate embeddings:

```bash
python rag/embeddings/create_embeddings.py
```

Build vector index:

```bash
python rag/vector_store/build_index.py
```

Test retrieval:

```bash
python rag/retrieval/test_retrieval.py
```

---

# 📱 Run Android Application

1. Open **Android Studio**
2. Open the `android-app/` directory
3. Allow Gradle synchronization
4. Configure Firebase
5. Configure the backend URL
6. Start an Android emulator or connect a physical device
7. Click **Run**

For an Android Emulator, a local Spring Boot server usually uses:

```text
http://10.0.2.2:8080
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

FIREBASE_PROJECT_ID=
FIREBASE_STORAGE_BUCKET=

MAPS_API_KEY=
```

Sensitive files should not be committed:

```text
.env
google-services.json
service-account.json
API keys
private keys
database passwords
```

Add them to `.gitignore`.

---

# 🚫 No External LLM API for YatraLM

The core educational LLM will **not depend on OpenAI or Gemini API keys**.

The initial YatraLM implementation will use:

```text
Python
+
PyTorch
+
Custom Tokenizer
+
Custom Transformer
+
Tourism Dataset
```

External services may still be used for infrastructure features such as maps, weather, authentication, or other APIs depending on the final project requirements.

The goal is to understand and implement the LLM ourselves.

---

# 🧪 Testing

## Android

```text
Unit Tests
UI Tests
Navigation Tests
API Tests
```

## Spring Boot

```text
Controller Tests
Service Tests
Repository Tests
Integration Tests
```

## YatraLM

Test:

```text
Tokenizer
Attention
Transformer
Training
Generation
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

YatraVerse should implement:

- Authentication
- Authorization
- Input validation
- Secure password handling
- JWT/security controls
- HTTPS in production
- Database access control
- API rate limiting
- Firebase security rules
- Secure file upload
- No credentials in GitHub

Emergency functionality should not depend on an AI-generated response.

---

# 🚧 Development Roadmap

## Phase 1 — Project Setup

```text
Repository
Android Project
Spring Boot
PostgreSQL
Firebase
Python AI Environment
```

## Phase 2 — Basic Application

```text
Login
Signup
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

## Phase 4 — YatraLM

```text
Tokenizer
     ↓
Embeddings
     ↓
Self Attention
     ↓
Multi-Head Attention
     ↓
Transformer Block
     ↓
YatraLM
     ↓
Training
     ↓
Generation
```

## Phase 5 — RAG

```text
Tourism Documents
     ↓
Chunking
     ↓
Embeddings
     ↓
FAISS
     ↓
Retriever
     ↓
YatraLM
```

## Phase 6 — AI Features

```text
Heritage Scanner
Crowd Prediction
Eco Score
AI Trip Planner
Smart Itinerary
```

## Phase 7 — Community

```text
Posts
Comments
Likes
Reviews
Photos
```

## Phase 8 — Marketplace

```text
Guides
Homestays
Artisans
Experiences
Bookings
```

## Phase 9 — Analytics

```text
Visitor Trends
Destination Popularity
Booking Analytics
Feedback
Crowd Analytics
```

## Phase 10 — Deployment

```text
Docker
     ↓
AI Service
     ↓
Spring Boot
     ↓
PostgreSQL
     ↓
Android
```

---

# 🏆 YatraLM Milestones

- [ ] Tokenizer works
- [ ] Embeddings work
- [ ] Self-attention works
- [ ] Multi-head attention works
- [ ] Transformer block works
- [ ] YatraLM forward pass works
- [ ] Model trains successfully
- [ ] Model generates text
- [ ] Tourism-domain training
- [ ] RAG integration
- [ ] Spring Boot integration
- [ ] Android integration

---

# 🔮 Future Scope

Future versions of YatraVerse AI can include:

```text
Multilingual YatraLM
        ↓
Voice-Based Travel Assistant
        ↓
Speech-to-Text
        ↓
Text-to-Speech
        ↓
AI Travel Agent
        ↓
Tool Calling
        ↓
Real-Time Itinerary Adaptation
        ↓
Personalized Recommendations
        ↓
On-Device AI
```

Other possibilities:

- Hindi and regional-language tourism
- Voice-based trip planning
- Offline tourism assistant
- AI travel agent
- AR heritage exploration
- Real-time route optimization
- Advanced crowd forecasting
- Carbon emission estimation
- Personalized recommendation engine
- Local business recommendation
- Tourism demand forecasting

---

# 📊 Final Architecture

```text
                    YATRAVERSE AI
                          │
          ┌───────────────┼────────────────┐
          │               │                │
          ▼               ▼                ▼
       Android        Spring Boot       AI Service
          │               │                │
          │               ▼                │
          │           PostgreSQL           │
          │                                │
          │               ┌────────────────┤
          │               │                │
          │               ▼                ▼
          │            YatraLM             ML
          │               │                │
          │               ▼                ├── Crowd
          │              RAG                └── Eco
          │               │
          │               ▼
          │       Tourism Knowledge
          │
          └──────── Firebase / Maps
```

---

# 🎓 Learning Objective

The most important goal of this project is to understand the complete modern AI development pipeline:

```text
DATA
 ↓
TOKENIZATION
 ↓
EMBEDDINGS
 ↓
TRANSFORMER
 ↓
TRAINING
 ↓
YatraLM
 ↓
VECTOR EMBEDDINGS
 ↓
VECTOR SEARCH
 ↓
RAG
 ↓
AI SERVICE
 ↓
SPRING BOOT
 ↓
POSTGRESQL
 ↓
ANDROID APPLICATION
```

This allows the team to gain practical experience in:

- Android Development
- Backend Development
- REST API Development
- Database Engineering
- Machine Learning
- Computer Vision
- Natural Language Processing
- Transformer Architecture
- LLM Engineering
- RAG
- Vector Databases
- AI Evaluation
- Model Deployment
- Git/GitHub Collaboration

---

# 📌 Project Status

```text
🚧 Under Development
```

YatraVerse AI is being developed as a collaborative academic and learning project focused on building an end-to-end smart tourism ecosystem using Android, Spring Boot, PostgreSQL, Firebase, Machine Learning, Computer Vision, a custom tourism-domain LLM, and Retrieval-Augmented Generation.

---

# ⭐ Final Goal

> **YatraVerse AI aims to become an intelligent, personalized, safe, sustainable, and culturally aware tourism platform while providing the development team with hands-on experience in modern software engineering and AI/LLM engineering.**
