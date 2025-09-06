package com.greenplan.generation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.UUID;

public interface ConceptGeneratorService {
    void generateConcepts(UUID projectId) throws Exception;
}
