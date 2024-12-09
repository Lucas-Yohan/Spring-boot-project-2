package com.project2.spring_boot_project_2.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal price) {

}
