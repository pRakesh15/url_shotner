package com.bitly.urlShotner.controller;

import com.bitly.urlShotner.dto.UrlMappingDto;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.service.UrlMappingService;
import com.bitly.urlShotner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/urls")
@AllArgsConstructor
public class UrlMappingController {

    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorter")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String,String> request, Principal principal){
        String originalUrl=request.get("originalUrl");
        User user=userService.findByUserName(principal.getName());
 UrlMappingDto urlMappingDto=urlMappingService.createSortUrl(originalUrl,user);
 return ResponseEntity.ok(urlMappingDto);


    }

}
