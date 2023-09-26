package de.stinner.anmeldetool.hexagonal.domain.error.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TestControllerDto {
    @NotBlank
    @Size(max = 5)
    String name;
}
