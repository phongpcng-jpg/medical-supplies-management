package io.github.phongpcng_jpg.medical_supplies_management.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.wrappers.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler.
 *
 * @author Nguyen Que Phong
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(
            ApiException ex) {

        ErrorCode errorCode = ex.getErrorCode();

        log.warn(
                "Business exception [{}]: {}",
                errorCode.getCode(),
                ex.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(
                        ApiResponse.error(
                                errorCode.getStatus(),
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            errors.put(
                    error.getField(),
                    error.getDefaultMessage());

        }

        log.warn("Validation failed: {}", errors);

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.error(
                                ErrorCode.VALIDATION_FAILED.getStatus(),
                                ErrorCode.VALIDATION_FAILED.getMessage(),
                                errors
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception ex) {

        log.error("Unexpected exception.", ex);

        return ResponseEntity
                .internalServerError()
                .body(
                        ApiResponse.error(
                                ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
                                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                                null
                        )
                );
    }

}
