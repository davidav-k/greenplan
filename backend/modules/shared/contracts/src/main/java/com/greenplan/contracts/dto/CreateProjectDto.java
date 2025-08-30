package com.greenplan.contracts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProjectDto(
  @NotBlank String location,
  @Valid @NotNull DimensionsDto dimensionsDto,
  @NotBlank String sunExposure,
  @Valid @NotNull SoilDto soilDto,
  @NotBlank String style,
  @PositiveOrZero BigDecimal budgetEur,
  @NotBlank String maintenanceLevel,
  @Size(max = 2000) String notes
) {}
