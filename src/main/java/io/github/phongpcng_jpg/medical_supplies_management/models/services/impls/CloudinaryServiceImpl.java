package io.github.phongpcng_jpg.medical_supplies_management.models.services.impls;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.phongpcng_jpg.medical_supplies_management.exceptions.ApiException;
import io.github.phongpcng_jpg.medical_supplies_management.exceptions.ErrorCode;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.ImageUploadResult;
import io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces.ICloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CloudinaryServiceImpl implements ICloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public ImageUploadResult upload(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            return null;
        }

        try {

            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary.uploader().upload(
                    image.getBytes(),
                    ObjectUtils.emptyMap()
            );

            String imageUrl = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");

            log.info(
                    "Image uploaded successfully. Public ID: {}",
                    publicId
            );

            return ImageUploadResult.builder()
                    .imageUrl(imageUrl)
                    .publicId(publicId)
                    .build();

        } catch (IOException ex) {

            log.error("Failed to upload image.", ex);

            throw new ApiException(ErrorCode.IMAGE_UPLOAD_FAILED);

        }

    }

    @Override
    @Transactional
    public void delete(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {

            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            log.info(
                    "Image deleted successfully. Public ID: {}",
                    publicId
            );

        } catch (IOException ex) {

            log.error(
                    "Failed to delete image '{}'.",
                    publicId,
                    ex
            );

            throw new ApiException(ErrorCode.IMAGE_DELETE_FAILED);

        }

    }

}