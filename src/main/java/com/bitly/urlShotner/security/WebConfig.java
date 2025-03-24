package com.bitly.urlShotner.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${cors.url}")
    private String frontendUrl;

   @Bean
   public WebMvcConfigurer corsConfigurer() {
       return new WebMvcConfigurer() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**")
                       .allowedOrigins(frontendUrl) // Make sure this value is correctly set in properties
                       .allowedMethods("POST", "GET", "PUT", "DELETE")
                       .allowCredentials(true)
                       .allowedHeaders("*")
                       .maxAge(3600);
           }
       };
   }

}





