package io.github.phongpcng_jpg.medical_supplies_management.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration properties for integrating with the Cloudinary platform.
 *
 * <p>
 * This class binds all configuration values prefixed with
 * <b>{@code cloudinary}</b> from the application's external configuration
 * sources, such as:
 * </p>
 *
 * <ul>
 *     <li>{@code application.yml}</li>
 *     <li>{@code application.properties}</li>
 *     <li>Environment variables</li>
 *     <li>System properties</li>
 * </ul>
 *
 * <p>
 * These properties are used by {@link CloudinaryConfig} to create and configure
 * a singleton {@code Cloudinary} client that communicates with the Cloudinary API.
 * </p>
 *
 * <p>Example configuration:</p>
 *
 * <pre>{@code
 * cloudinary:
 *   cloud-name: demo
 *   api-key: 1234567890
 *   api-secret: your-secret
 * }</pre>
 *
 * @author Nguyen Que Phong
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see CloudinaryConfig
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
public class CloudinaryProperties {

    /**
     * Name of the Cloudinary cloud account.
     *
     * <p>
     * This value identifies the Cloudinary account that stores and manages
     * uploaded media resources.
     * </p>
     */
    private String cloudName;

    /**
     * API key used to authenticate requests to Cloudinary.
     *
     * <p>
     * The API key is publicly identifiable but should always be paired with
     * the corresponding API secret for authenticated operations.
     * </p>
     */
    private String apiKey;

    /**
     * Secret key used to sign authenticated requests.
     *
     * <p>
     * This value is sensitive and should never be exposed in logs,
     * source code, or client-side applications.
     * </p>
     */
    private String apiSecret;
    
}
