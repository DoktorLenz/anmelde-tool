package de.stinner.anmeldetool.application.rest.error;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TestControllerDto {
    @NotBlank
    @Size(max = 5)
    String name;
}
