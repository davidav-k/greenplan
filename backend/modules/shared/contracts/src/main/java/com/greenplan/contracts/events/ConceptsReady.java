package com.greenplan.contracts.events;

import java.util.UUID;

public record ConceptsReady(UUID projectId) implements Events {
}
