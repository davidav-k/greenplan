package com.greenplan.contracts.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record SoilDto(
        @NotBlank String type,
        @DecimalMin("0.0") @DecimalMax("14.0") double ph,
        @NotBlank String drainage
) {}
