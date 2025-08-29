package com.greenplan.contracts.dto;

import java.util.UUID;

/** Response DTO produced after project creation. */
public record ProjectCreatedResponse(UUID id, String status) {}
