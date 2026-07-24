package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import io.github.phongpcng_jpg.medical_supplies_management.utils.validations.annotations.FileExtension;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SupplyUpdateDTO {

    @Size(max = 100, message = "Supply name max size in 100")
    private String name;
    
    @Size(max = 255, message = "Supply specification max size in 255")
    private String specification;

    @Size(max = 150, message = "Supply provider name max size in 150")
    private String provider;

    @PositiveOrZero(message = "Supply unit must be greater than or equal to 0")
    private BigDecimal unit;

    @FileExtension(
        allowedExtensions = {".jpg",".png",".jpeg"},
        message = "Supply image has invalid file extension"
    )
    private MultipartFile image;

}
