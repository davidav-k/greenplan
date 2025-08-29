package com.greenplan.contracts.events;

import java.util.UUID;

public record ConceptsRequested(UUID projectId) implements Events {
}
