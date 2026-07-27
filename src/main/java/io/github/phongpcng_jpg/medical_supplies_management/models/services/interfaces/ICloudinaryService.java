package io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.ImageUploadResult;

public interface ICloudinaryService {

    ImageUploadResult upload(MultipartFile image);

    void delete(String publicId);

}
