package com.greenplan.generation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenplan.contracts.events.ConceptsReady;
import com.greenplan.generation.OpenAiClient;
import com.greenplan.project.Project;
import com.greenplan.project.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConceptGeneratorServiceImpl implements ConceptGeneratorService {

    private final OpenAiClient openAiClient;
    private final ProjectRepository projects;
    private final ObjectMapper json;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public void generateConcepts(UUID projectId) throws Exception {
        log.info("Generating concepts for project: {}", projectId);

        Project p = projects.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Project with id " + projectId + " not found"));

        String system = loadPrompt("prompts/concepts_system.txt");
        String userTemplate = loadPrompt("prompts/concepts_user.txt");
        String user = userTemplate.replace("{input}", p.getInput());

        Map<String, Object> schema = Map.of(
            "type", "object",
            "properties", Map.of(
                "concepts", Map.of(
                    "type", "array",
                    "minItems", 3,
                    "maxItems", 3,
                    "items", Map.of(
                        "type", "object",
                        "properties", Map.of(
                            "name", Map.of("type", "string"),
                            "description", Map.of("type", "string"),
                            "imagePrompt", Map.of("type", "string")
                        ),
                        "required", new String[]{"name", "description", "imagePrompt"}
                    )
                )
            ),
            "required", new String[]{"concepts"},
            "additionalProperties", false
        );

        String rawResponse = openAiClient.generateStrictJson(system, user, schema);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = json.readValue(rawResponse, Map.class);

        @SuppressWarnings("unchecked")
        java.util.List<Object> choices = (java.util.List<Object>) responseMap.get("choices");

        @SuppressWarnings("unchecked")
        Map<String, Object> firstChoice = (Map<String, Object>) choices.get(0);

        @SuppressWarnings("unchecked")
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

        String content = (String) message.get("content");

        @SuppressWarnings("unchecked")
        Map<String, Object> conceptsJson = json.readValue(content, Map.class);

        p.setConcepts(json.writeValueAsString(conceptsJson));
        p.setStatus("CONCEPTS_READY");
        projects.save(p);

        publisher.publishEvent(new ConceptsReady(projectId));

        log.info("Concepts generated and saved for project: {}", projectId);
    }

    private String loadPrompt(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }
}
