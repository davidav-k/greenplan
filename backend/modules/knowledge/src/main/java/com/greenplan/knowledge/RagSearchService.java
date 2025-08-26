package com.greenplan.knowledge;

import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RagSearchService {
  private final NamedParameterJdbcTemplate jdbc;
  public RagSearchService(NamedParameterJdbcTemplate jdbc){ this.jdbc = jdbc; }

  public List<Map<String,Object>> search(UUID projectId, float[] queryEmb, int k){
    var sql = """
              SELECT id, document_id, chunk_index, text, (embedding <=> :emb) AS distance
              FROM knowledge_chunk
              WHERE project_id = :pid
              ORDER BY embedding <=> :emb
              LIMIT :k
            """;
    var params = new MapSqlParameterSource()
      .addValue("pid", projectId)
      .addValue("emb", new PgVector(queryEmb))
      .addValue("k", k);
    return jdbc.queryForList(sql, params);
  }
}