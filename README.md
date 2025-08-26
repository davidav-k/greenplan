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
- app: HTTP API, bean config
- assets: MinIO facade + presigned URL
- knowledge: pgvector + search
- chat: conversation history
- project/generation/authoring/render: domain boundaries
