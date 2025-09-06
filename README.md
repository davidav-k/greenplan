## Greenplan

Greenplan is a modular Java/Spring Boot monorepo for “green planning”: projects, knowledge/RAG, LLM-based generation/authoring, asset storage, and document rendering. It ships with local infrastructure (PostgreSQL + pgvector, MinIO, Ollama) and a minimal SSR frontend.

## Stack
- Java 21, Spring Boot 3.3.x, Maven
- PostgreSQL (pgvector), Flyway
- MinIO (S3-compatible storage)
- Ollama (local LLM runtime) and optional cloud LLMs
- Docker Compose for local infra

## Repository layout
```
backend/
  modules/
    app/            # API gateway / entrypoint (aggregates domain modules)
    assets/         # Asset storage integration (MinIO)
    authoring/      # Local LLM authoring utilities
    chat/           # Chat, SSE, dialogue memory
    generation/     # Text generation utilities
    knowledge/      # RAG: docs, chunks, embeddings (pgvector)
    project/        # Project management (projects, tasks, states)
    render/         # HTML→PDF, XLSX, SVG→PNG
    shared/
      contracts/    # Shared DTOs/contracts
      infra/        # Infra utilities shared by modules
frontend/           # Spring Boot + Thymeleaf SSR
infrastructure/
  db/               # Postgres data and init
  minio/            # MinIO data
  ollama/           # Ollama state (models, keys)
compose.yml         # Local stack and app services
pom.xml             # Parent POM (dependency management)
```

Notes:
- The `app` module depends on all other backend modules; root `pom.xml` provides dependency management.

## Services and default ports
- API (`backend/modules/app`): `8181` (health: `/actuator/health`)
- Frontend (`frontend`): `8081`
- PostgreSQL: host `${DB_PORT:-5532}` → container `5432`
- MinIO: API `${MINIO_PORT:-9000}`, Console `${MINIO_CONSOLE_PORT:-9001}`
- Ollama: `${OLLAMA_PORT:-11434}`

## Quick start (Docker Compose)
Prereqs: Docker Desktop 4+, Java 21 (for local builds if not using prebuilt images).

1) Build frontend JAR (required before first run):
```bash
mvn clean package -pl frontend -DskipTests
```

2) Start infra and apps:
```bash
docker compose up -d
```

3) Open:
- Frontend: http://localhost:8081/
- API health: http://localhost:8181/actuator/health
- MinIO Console: http://localhost:9001/ (default: minioadmin / minioadmin)

To stop:
```bash
docker compose down
```

## Environment configuration
Override via environment or a `.env` file at repo root.

Infrastructure:
- `POSTGRES_DB` (default `greenplan`)
- `POSTGRES_USER` (default `gp_user`)
- `POSTGRES_PASSWORD` (default `gp_pass`)
- `DB_PORT` (host port, default `5532`)
- `MINIO_ROOT_USER`, `MINIO_ROOT_PASSWORD` (default `minioadmin` / `minioadmin`)
- `MINIO_PORT` (default `9000`), `MINIO_CONSOLE_PORT` (default `9001`)
- `OLLAMA_PORT` (default `11434`)

API (`app`):
- `SPRING_PROFILES_ACTIVE` (default `dev`)
- `DB_URL` (default `jdbc:postgresql://db:5432/${POSTGRES_DB}` in containers; `jdbc:postgresql://localhost:5532/greenplan` locally)
- `DB_USER`, `DB_PASS`
- `MINIO_ENDPOINT` (default `http://minio:9000` in containers; `http://localhost:9000` locally)
- `MINIO_ACCESS_KEY`, `MINIO_SECRET_KEY`, `MINIO_BUCKET` (default `greenplan`)
- `OLLAMA_BASE_URL` (default `http://ollama:11434` in containers; `http://localhost:11434` locally)
- `OLLAMA_EMBED_MODEL` (default `mxbai-embed-large`)
- `CLOUD_LLM_BASE_URL` (default `https://api.openai.com/v1`)
- `CLOUD_LLM_MODEL` (default `gpt-4o-mini`)
- `OPENAI_API_KEY` (default `changeme` – set a real key to enable cloud LLMs)

Frontend:
- `SPRING_PROFILES_ACTIVE` (default `dev`)
- `FRONTEND_PORT` (default `8081`)

## Local development (without Docker)
Start infra with Compose, then run apps from your IDE/CLI.

1) Infra only:
```bash
docker compose up -d db minio ollama
```

2) API (from project root):
```bash
mvn -f backend/modules/app spring-boot:run
```

3) Frontend (from project root):
```bash
mvn -f frontend spring-boot:run
```

Build jars:
```bash
mvn -f backend/modules/app clean package
mvn -f frontend clean package
```

## Data directories
- PostgreSQL data: `infrastructure/db/data`
- PostgreSQL init scripts: `infrastructure/db/init`
- MinIO data: `infrastructure/minio/data`
- Ollama data/models: `infrastructure/ollama`

## Modules overview
- `app`: Spring Boot API, aggregates domain modules, exposes REST, configures Flyway and datasource.
- `shared/contracts`: shared DTOs and contracts.
- `shared/infra`: shared infra helpers.
- `assets`: MinIO integration for object storage.
- `authoring`: local LLM utilities for document/typography authoring.
- `chat`: chat/SSE and persisted dialogue memory.
- `generation`: LLM text generation utilities.
- `knowledge`: RAG—documents, chunking, embeddings, search on pgvector.
- `project`: project/task/workflow management.
- `render`: document rendering (HTML→PDF, XLSX, SVG→PNG).

## Health checks
All services use unified health check configuration:
- **interval**: 10s
- **timeout**: 5s  
- **retries**: 10 (PostgreSQL: 30 for stability)

Service-specific checks:
- **API**: `wget` → `/actuator/health`
- **Frontend**: `wget` → `/` (root endpoint)
- **MinIO**: `curl` → `/minio/health/live`
- **PostgreSQL**: `pg_isready` command
- **Ollama**: `ollama list` command

## Startup order
Services start in dependency order:
1. **Infrastructure**: `db`, `minio`, `ollama` (parallel)
2. **API**: `gp-api` (waits for all infrastructure)
3. **Frontend**: `gp-frontend` (waits for all services including API)


 
