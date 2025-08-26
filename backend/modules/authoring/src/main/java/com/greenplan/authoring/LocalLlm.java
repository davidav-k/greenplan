package com.greenplan.authoring;

import org.springframework.stereotype.Component;

@Component
public class LocalLlm {

    public String generateContent(String prompt) {
        // TODO: Реализовать интеграцию с Ollama
        return "Generated content for: " + prompt;
    }
}