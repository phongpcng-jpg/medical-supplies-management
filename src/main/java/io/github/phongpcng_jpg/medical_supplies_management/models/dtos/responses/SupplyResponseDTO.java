package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SupplyResponseDTO {

    private Long id;
    
    private String name;
    
    private String specification;
    
    private String provider;
    
    private BigDecimal unit;
    
    private Integer quantity;
    
    private String imageUrl;
    
    private Instant createdAt;
    
    private Instant updatedAt;

}
