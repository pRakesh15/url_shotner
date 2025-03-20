package com.bitly.urlShotner.service;

import com.bitly.urlShotner.dto.ClickEventDto;
import com.bitly.urlShotner.dto.UrlMappingDto;
import com.bitly.urlShotner.models.ClickEvent;
import com.bitly.urlShotner.models.UrlMapping;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.ClickEventReppositry;
import com.bitly.urlShotner.repositroy.UrlMappingRepositry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepositry urlMappingRepositry;

    private ClickEventReppositry clickEventReppositry;
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


    //create  a methos for find url of a user
    public List<UrlMappingDto> getUrlsOfUser(User user) {
       return  urlMappingRepositry.findByUser(user).stream()
               .map(this::convertUrlMapping)
               .toList();

    }


    public List<ClickEventDto> getClickEventsByDate(String sortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping bySortUrl = urlMappingRepositry.findBySortUrl(sortUrl);
        if(bySortUrl!=null){
              return  clickEventReppositry.findByUrlMappingAndClickDateBetween(bySortUrl,start,end).stream()
                      .collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(),Collectors.counting()))
                      .entrySet().stream()
                      .map(entry->{
     ClickEventDto clickEventDto=new ClickEventDto();
     clickEventDto.setClickDate(entry.getKey());
     clickEventDto.setCount(entry.getValue());
     return clickEventDto;
                      })
                      .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Map<LocalDate, Long> getAllClickEventOfUser(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings=urlMappingRepositry.findByUser(user);
        List<ClickEvent> clickEventList=clickEventReppositry.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),end.plusDays(1).atStartOfDay());
    return clickEventList.stream()
            .collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(),Collectors.counting()));
    }

    public UrlMapping getOrirginalUrl(String sortUrl) {
        UrlMapping bySortUrl = urlMappingRepositry.findBySortUrl(sortUrl);
        if(bySortUrl!=null){
            bySortUrl.setClickCount(bySortUrl.getClickCount()+1);
            urlMappingRepositry.save(bySortUrl);
            //have to record the clickevent
            ClickEvent clickEvent=new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(bySortUrl);
            clickEventReppositry.save(clickEvent);
        }
        return bySortUrl;
    }
}
