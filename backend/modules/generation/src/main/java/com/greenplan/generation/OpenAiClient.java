package com.greenplan.generation;

import org.springframework.http.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class OpenAiClient implements CloudLlm {
    private final RestClient http;
    private final String model;

    public OpenAiClient(String baseUrl, String apiKey, String model) {
        this.model = model;
        this.http = RestClient.builder().baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey).build();
    }

    @Override
    public String generateStrictJson(String system, String user, Map<String, Object> schema) {
        var req = Map.of(
                "model", model,
                "response_format", Map.of("type", "json_schema", "json_schema", Map.of("name", "Result", "schema", schema)),
                "messages", new Object[]{Map.of("role", "system", "content", system), Map.of("role", "user", "content", user)}
        );
        return http.post().uri("/chat/completions").body(req).retrieve().body(String.class);
    }
}