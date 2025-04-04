package com.cenfotec.p3.neuralforge_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for project material file downloads.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Configuration
public class ProjectMaterialConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}/uploads}")
    private String baseUploadDir;
    
    private final String materialUploadDir = "materials";
} 