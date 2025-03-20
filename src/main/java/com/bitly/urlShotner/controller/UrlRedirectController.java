package com.bitly.urlShotner.controller;

import com.bitly.urlShotner.models.UrlMapping;
import com.bitly.urlShotner.repositroy.UrlMappingRepositry;
import com.bitly.urlShotner.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UrlRedirectController {

    private UrlMappingService urlMappingService;

    @GetMapping("/{sortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String sortUrl){
        UrlMapping urlMapping=urlMappingService.getOrirginalUrl(sortUrl);
        if(urlMapping!=null){
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.add("Location",urlMapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(httpHeaders).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
