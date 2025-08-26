package com.greenplan.contracts.events;

import java.util.UUID;

public sealed interface Events permits DocumentsRequested, DocumentsReady, ConceptsRequested, ConceptsReady {
}

public record ConceptsRequested(UUID projectId) implements Events {
}

public record ConceptsReady(UUID projectId) implements Events {
}

public record DocumentsRequested(UUID projectId) implements Events {
}

public record DocumentsReady(UUID projectId) implements Events {
}