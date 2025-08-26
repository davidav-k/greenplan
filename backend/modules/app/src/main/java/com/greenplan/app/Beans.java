package com.greenplan.app;

import com.greenplan.assets.StorageService;
import com.greenplan.generation.OllamaClient;
import com.greenplan.generation.OpenAiClient;
import com.greenplan.render.SimpleRender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class Beans {
    @Bean
    StorageService storage(
            @Value("${app.storage.endpoint}") String e,
            @Value("${app.storage.access}") String a,
            @Value("${app.storage.secret}") String s,
            @Value("${app.storage.bucket}") String b) {
        return new StorageService(e, a, s, b);
    }


    @Bean
    OpenAiClient cloudLlm(@Value("${app.openai.baseUrl}") String base,
                          @Value("${app.openai.apiKey}") String key,
                          @Value("${app.openai.model}") String model) {
        return new OpenAiClient(base, key, model);
    }

    @Bean
    SimpleRender render() {
        return new SimpleRender();
    }
}