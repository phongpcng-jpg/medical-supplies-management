package io.github.phongpcng_jpg.medical_supplies_management.utils.validations.handles;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import io.github.phongpcng_jpg.medical_supplies_management.utils.validations.annotations.FileExtension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileExtensionValidator 
    implements ConstraintValidator<FileExtension, MultipartFile>{
    
    private String[]  allowedExtensions;

    @Override
    public void initialize(FileExtension constraintAnnotation) {
        allowedExtensions = constraintAnnotation.allowedExtensions();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        
        if(value == null || value.isEmpty()){
            return false;
        }

        String fileName = value.getOriginalFilename();
        if(fileName == null){
            return false;
        }

        String fileNameLowerCase = fileName.toLowerCase();
        return Arrays.stream(allowedExtensions)
                .anyMatch(fileNameLowerCase::endsWith);

    }

}
