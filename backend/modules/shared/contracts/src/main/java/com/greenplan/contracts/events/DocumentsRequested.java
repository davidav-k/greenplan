package com.greenplan.contracts.events;

import java.util.UUID;

public record DocumentsRequested(UUID projectId) implements Events {
}
