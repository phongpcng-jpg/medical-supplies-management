package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResult {

    private String imageUrl;

    private String publicId;

}