package io.github.phongpcng_jpg.medical_supplies_management.utils.mappers;

import java.util.Collections;
import java.util.List;

import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.SupplyResponseDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Supply;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SupplyMapper {

    public static SupplyResponseDTO toResponseDTO(Supply supply) {

        if (supply == null) {
            return null;
        }

        return SupplyResponseDTO.builder()
                .id(supply.getId())
                .name(supply.getName())
                .specification(supply.getSpecification())
                .provider(supply.getProvider())
                .unit(supply.getUnit())
                .quantity(supply.getQuantity())
                .imageUrl(supply.getImageUrl())
                .createdAt(supply.getCreatedAt())
                .updatedAt(supply.getUpdatedAt())
                .build();

    }

    public static List<SupplyResponseDTO> toResponseDTOList(
            List<Supply> supplies) {

        if (supplies == null || supplies.isEmpty()) {
            return Collections.emptyList();
        }

        return supplies.stream()
                .map(SupplyMapper::toResponseDTO)
                .toList();

    }

}
