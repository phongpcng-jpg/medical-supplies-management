package io.github.phongpcng_jpg.medical_supplies_management.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties(CloudinaryProperties.class)
@Slf4j
public class CloudinaryConfig {

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
