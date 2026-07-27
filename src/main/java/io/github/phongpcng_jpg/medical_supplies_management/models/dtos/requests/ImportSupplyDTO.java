package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportSupplyDTO {

    @NotNull(message = "Import amount is required")
    @Positive(message = "Import amount must be greater than 0")
    private Integer amount;

}
