package com.greenplan.generation;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import java.util.Map;

public class OllamaClient {
    private final RestClient http;
    private final String model;

    public OllamaClient(String baseUrl, String model) {
        this.model = model;
        this.http = RestClient.builder().baseUrl(baseUrl).build();
    }

    public String generate(String prompt) {
        var req = Map.of("model", model, "prompt", prompt, "stream", false);
        return http.post().uri("/api/generate").body(req).retrieve().body(String.class);
    }
}