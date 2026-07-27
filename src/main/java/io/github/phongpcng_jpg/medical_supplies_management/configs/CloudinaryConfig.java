package io.github.phongpcng_jpg.medical_supplies_management.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring configuration responsible for creating and configuring the
 * Cloudinary client.
 *
 * <p>
 * This configuration registers a singleton {@link Cloudinary} bean in the
 * Spring IoC container using the credentials provided by
 * {@link CloudinaryProperties}.
 * </p>
 *
 * <p>
 * Once initialized, the {@code Cloudinary} bean can be injected into services
 * responsible for media upload, deletion, transformation, and asset management.
 * </p>
 *
 * <p>
 * Configuration values are automatically loaded from the application's
 * external configuration through {@link CloudinaryProperties}.
 * </p>
 *
 * @author Nguyen Que Phong
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see Cloudinary
 * @see CloudinaryProperties
 * @see org.springframework.context.annotation.Configuration
 */
@Configuration
@EnableConfigurationProperties(CloudinaryProperties.class)
@Slf4j
public class CloudinaryConfig {

    /**
     * Creates and registers the application's Cloudinary client.
     *
     * <p>
     * The client is configured using the Cloudinary credentials defined in
     * {@link CloudinaryProperties}. The resulting instance is managed as a
     * singleton Spring bean and reused throughout the application's lifetime.
     * </p>
     *
     * <p>
     * A successful initialization is logged with the configured cloud name.
     * Sensitive information such as the API key and API secret is never logged.
     * </p>
     *
     * @param properties configuration properties containing the Cloudinary
     *                   cloud name, API key, and API secret
     *
     * @return a fully configured singleton {@link Cloudinary} instance
     *
     * @see CloudinaryProperties
     * @see Cloudinary
     */
    @Bean
    public Cloudinary cloudinary(CloudinaryProperties properties) {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", properties.getCloudName(),
                "api_key", properties.getApiKey(),
                "api_secret", properties.getApiSecret()
        ));

        log.info(
            "Cloudinary initialized successfully for cloud '{}'", 
            properties.getCloudName()
        );

        return cloudinary;

    }
    
}
