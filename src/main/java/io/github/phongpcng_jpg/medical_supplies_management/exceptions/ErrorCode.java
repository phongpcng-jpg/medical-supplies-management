package io.github.phongpcng_jpg.medical_supplies_management.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Defines all application error codes.
 *
 * <p>Each error code contains:
 * <ul>
 *     <li>HTTP status</li>
 *     <li>Application error code</li>
 *     <li>Default message</li>
 * </ul>
 *
 * @author Nguyen Que Phong
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /*
     * ========= Success =========
     */

    SUCCESS(
            HttpStatus.OK,
            "SUCCESS",
            "Request completed successfully."
    ),

    CREATED(
            HttpStatus.CREATED,
            "CREATED",
            "Resource created successfully."
    ),

    /*
     * ========= Validation =========
     */

    VALIDATION_FAILED(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_FAILED",
            "Validation failed."
    ),

    INVALID_REQUEST(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST",
            "Invalid request."
    ),

    /*
     * ========= Supply =========
     */

    SUPPLY_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "SUPPLY_NOT_FOUND",
            "Supply not found."
    ),

    SUPPLY_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "SUPPLY_ALREADY_EXISTS",
            "Supply already exists."
    ),

    INSUFFICIENT_QUANTITY(
            HttpStatus.BAD_REQUEST,
            "INSUFFICIENT_QUANTITY",
            "Insufficient quantity."
    ),

    /*
     * ========= Image =========
     */

    IMAGE_UPLOAD_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "IMAGE_UPLOAD_FAILED",
            "Failed to upload image."
    ),

    IMAGE_DELETE_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "IMAGE_DELETE_FAILED",
            "Failed to delete image."
    ),

    /*
     * ========= Server =========
     */

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred."
    ),

    NO_TRANSACTION_DATA(
            HttpStatus.NOT_FOUND,
            "NO_TRANSACTION_DATA",
            "No transaction data available."
    );

    /**
     * HTTP status.
     */
    private final HttpStatus status;

    /**
     * Application code.
     */
    private final String code;

    /**
     * Default message.
     */
    private final String message;

}
