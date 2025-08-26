# greenplan 

## Launch
1. 
```bash
docker compose -f docker/compose.yml up -d db minio ollama
```
2. (optional) pull in embeddings model: docker exec -it <ollama> ollama pull mxbai-embed-large
3. docker compose -f docker/compose.yml up -d api
4. Check: curl http://localhost:8181/api/projects -X POST -H "Content-Type: application/json" -d '{"location":"Helsinki, FI"}'

## Modules
- app: Web/API layer (entrypoint)
- assets: Asset management - images, files, etc (MinIO)
- authoring: local LLM (typography documents, etc)
- chat: Chat module - chat with the user, dialogue history (persisted memory), SSE.
- generation: LLM generation module (text generation, etc)
- knowledge: RAG - documents, chunks, embeddings, search. (Postgres + pgvector)
- projects: Project management (projects, tasks, etc) projects/parameters/workflow statuses.
- render: HTML → PDF, XLSX, SVG → PNG.
---
  soon:
- catalog — plants/catalogs/prices, reference books
