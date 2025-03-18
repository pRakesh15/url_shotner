package com.bitly.urlShotner.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDto {
    private  Long id;
    private  String originalUrl;
    private  String sortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String username;
}
