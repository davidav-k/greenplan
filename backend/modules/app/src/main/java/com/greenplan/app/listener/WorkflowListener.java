package com.greenplan.app.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenplan.contracts.events.ConceptsRequested;
import com.greenplan.generation.service.ConceptGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkflowListener {

  private final ConceptGeneratorService conceptService;

@EventListener
@Async
public void onConceptsRequested(ConceptsRequested event) {
    try {
        conceptService.generateConcepts(event.projectId());
    } catch (Exception e) {
        log.error("Failed to generate concepts for project: {}", event.projectId(), e);
        // Handle the error appropriately - maybe publish a failure event
    }
}

}
