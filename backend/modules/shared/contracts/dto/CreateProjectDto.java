package com.greenplan.contracts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProjectDto(
  @NotBlank String location,
  @Valid @NotNull Dimensions dimensions,
  @NotBlank String sunExposure,
  @Valid @NotNull Soil soil,
  @NotBlank String style,
  @PositiveOrZero BigDecimal budgetEur,
  @NotBlank String maintenanceLevel,
  @Size(max = 2000) String notes
) {
  public record Dimensions(
    @Positive double widthM,
    @Positive double lengthM,
    @NotBlank String shape
  ) {}

  public record Soil(
    @NotBlank String type,
    @DecimalMin("0.0") @DecimalMax("14.0") double ph,
    @NotBlank String drainage
  ) {}
}
