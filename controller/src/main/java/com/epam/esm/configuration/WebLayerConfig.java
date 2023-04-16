package com.epam.esm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring MVC web layer.
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebLayerConfig implements WebMvcConfigurer {

}
