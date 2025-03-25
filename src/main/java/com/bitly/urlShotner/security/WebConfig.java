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
               registry.addMapping("/**")  // Allow all endpoints
                       .allowedOrigins("http://localhost:5173")  // Allow frontend
                       .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                       .allowedHeaders("Authorization", "Content-Type") // Allow Authorization header
                       .allowCredentials(true); // Allow cookies (if needed)
           }
       };
   }

}





