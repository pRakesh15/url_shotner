package com.bitly.urlShotner.service;

import com.bitly.urlShotner.dto.UrlMappingDto;
import com.bitly.urlShotner.models.UrlMapping;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.UrlMappingRepositry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepositry urlMappingRepositry;
    public UrlMappingDto createSortUrl(String originalUrl, User user){

        String sortUrl=generateSortUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setSortUrl(sortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        //here we save the urlMapping.
        UrlMapping savedUrlMapping=urlMappingRepositry.save(urlMapping);

//        we have to return the UrlMappingDto which is a belittle  different from model
        return  convertUrlMapping(savedUrlMapping);

    }

    private String generateSortUrl() {
        String charaters="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sortUrl=new StringBuilder(8);
        for(int i=0;i<8;i++){
            sortUrl.append(charaters.charAt(random.nextInt(charaters.length())));
        }
        return  sortUrl.toString();
    }

    //create a method who can convert the urlMapping object to urlMappingDto object.
    private UrlMappingDto convertUrlMapping(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto=new UrlMappingDto();
        urlMappingDto.setId(urlMapping.getId());
        urlMappingDto.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDto.setSortUrl(urlMapping.getSortUrl());
        urlMappingDto.setClickCount(urlMapping.getClickCount());
        urlMappingDto.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDto.setUsername(urlMapping.getUser().getUsername());

        return  urlMappingDto;
    }
}
