package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses;

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
public class TopExportDTO {

    private Long topSupplyId;

    private String topSupplyName;

    private Integer totalExportQuantity;

}
