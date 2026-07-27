package io.github.phongpcng_jpg.medical_supplies_management.exceptions;

import lombok.Getter;

/**
 * Base runtime exception for the application.
 *
 * <p>This exception wraps an {@link ErrorCode} and optionally
 * allows overriding the default message.</p>
 *
 * @author Nguyen Que Phong
 * @since 1.0.0
 */
@Getter
public class ApiException extends RuntimeException {

    /**
     * Application error code.
     */
    private final ErrorCode errorCode;

    /**
     * Creates an exception using the default message
     * defined by the error code.
     *
     * @param errorCode application error code
     */
    public ApiException(ErrorCode errorCode) {

        super(errorCode.getMessage());

        this.errorCode = errorCode;

    }

    /**
     * Creates an exception using a custom message.
     *
     * @param errorCode application error code
     * @param message custom message
     */
    public ApiException(
            ErrorCode errorCode,
            String message) {

        super(message);

        this.errorCode = errorCode;

    }

}
