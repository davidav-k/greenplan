package com.greenplan.contracts.events;

import java.util.UUID;

public record DocumentsReady(UUID projectId) implements Events {
}
