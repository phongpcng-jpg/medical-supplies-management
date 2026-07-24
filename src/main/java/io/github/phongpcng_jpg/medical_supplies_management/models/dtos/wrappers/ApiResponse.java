package io.github.phongpcng_jpg.medical_supplies_management.models.dtos.wrappers;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Standard API response wrapper used for all REST endpoints.
 *
 * <p>This class provides a consistent response structure for both successful
 * and failed requests throughout the application.</p>
 *
 * <p>Typical success response:</p>
 * <pre>
 * {
 *   "success": true,
 *   "status": 200,
 *   "message": "Success.",
 *   "data": { ... },
 *   "timestamp": "2026-07-24T08:30:15Z"
 * }
 * </pre>
 *
 * <p>Typical error response:</p>
 * <pre>
 * {
 *   "success": false,
 *   "status": 400,
 *   "message": "Validation failed.",
 *   "errors": {
 *     "name": "Supply name is required."
 *   },
 *   "timestamp": "2026-07-24T08:30:15Z"
 * }
 * </pre>
 *
 * @param <T> response body type
 *
 * @author Nguyen Que Phong
 * @since 1.0.0
 * @version 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * Indicates whether the request was processed successfully.
     */
    private boolean success;

    /**
     * HTTP status code.
     */
    private int status;

    /**
     * Application response code.
     */
    private String code;

    /**
     * Human-readable response message.
     */
    private String message;

    /**
     * Response payload.
     *
     * <p>This field is {@code null} when the request fails.</p>
     */
    private T data;

    /**
     * Validation or business errors.
     *
     * <p>The key usually represents the field name or business key,
     * while the value contains the corresponding error message.</p>
     */
    private Map<String, String> errors;

    /**
     * Response creation timestamp (UTC).
     */
    @Builder.Default
    private Instant timestamp = Instant.now();

    /**
     * Creates a successful response with HTTP 200 (OK).
     *
     * @param data response payload
     * @param <T> payload type
     * @return success response
     */
    public static <T> ApiResponse<T> success(T data) {

        return success("Request completed successfully.", data);

    }

    /**
     * Creates a successful response with HTTP 200 (OK).
     *
     * @param message success message
     * @param data response payload
     * @param <T> payload type
     * @return success response
     */
    public static <T> ApiResponse<T> success(String message, T data) {

        return ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();

    }

    /**
     * Creates a successful response with HTTP 201 (Created).
     *
     * @param message success message
     * @param data response payload
     * @param <T> payload type
     * @return created response
     */
    public static <T> ApiResponse<T> created(String message, T data) {

        return ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build();

    }

    /**
     * Creates a successful response with HTTP 204 (No Content).
     *
     * @param message response message
     * @return no-content response
     */
    public static ApiResponse<Void> noContent(String message) {

        return ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.NO_CONTENT.value())
                .message(message)
                .build();

    }

    /**
     * Creates an error response.
     *
     * @param status HTTP status
     * @param message error message
     * @param errors validation or business errors
     * @return error response
     */
    public static ApiResponse<Void> error(
            HttpStatus status,
            String code,
            String message,
            Map<String, String> errors) {

        return ApiResponse.<Void>builder()
                .success(false)
                .status(status.value())
                .code(code)
                .message(message)
                .errors(errors)
                .build();

    }

}
