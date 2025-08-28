# greenplan
```markdown
made with ChatGPT
```
Green planning application with modular architecture.
___
### Project Structure

### Modules
- **app**: Web/API layer (entrypoint) - REST controllers and application configuration
- **contracts**: Shared DTOs and contracts between modules
- **assets**: Asset management - images, files, etc (MinIO)
- **authoring**: Local LLM (typography documents, etc)
- **chat**: Chat module - chat with the user, dialogue history (persisted memory), SSE
- **generation**: LLM generation module (text generation, etc)
- **knowledge**: RAG - documents, chunks, embeddings, search (Postgres + pgvector)
- **projects**: Project management (projects, tasks, etc) projects/parameters/workflow statuses
- **render**: HTML → PDF, XLSX, SVG → PNG

### Planned Modules
- **catalog**: Plants/catalogs/prices, reference books

## Technology Stack
- Java
- Spring Boot
- Maven
- PostgreSQL
- MinIO
- Ollama (LLM)

## Launch

1. Start infrastructure services:
```bash
docker compose -f docker/compose.yml up -d db minio ollama

