package com.greenplan.contracts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DimensionsDto(
  @Positive double widthM,
  @Positive double lengthM,
  @NotBlank String shape
) {}
