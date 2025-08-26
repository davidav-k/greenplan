package com.greenplan.contracts.dto;

import java.math.BigDecimal;

public record CreateProjectDto(
        String location,
        Dimensions dimensions,
        String sunExposure,
        Soil soil,
        String style,
        BigDecimal budgetEur,
        String maintenanceLevel,
        String notes
) {
    public record Dimensions(double widthM, double lengthM, String shape) {
    }

    public record Soil(String type, double ph, String drainage) {
    }
}