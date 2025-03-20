package com.bitly.urlShotner.controller;

import com.bitly.urlShotner.dto.ClickEventDto;
import com.bitly.urlShotner.dto.UrlMappingDto;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.service.UrlMappingService;
import com.bitly.urlShotner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    //controller for get all the urls of a particular user
    @GetMapping("/myUrls")
    @PreAuthorize("hasRole('ROLE_USER')")
    public  ResponseEntity<List<UrlMappingDto>> getMyUrls(Principal principal){
           User user=userService.findByUserName(principal.getName());
        List<UrlMappingDto> urlsOfUser = urlMappingService.getUrlsOfUser(user);
        return ResponseEntity.ok(urlsOfUser);
    }

    //controller for get the clickcunt of a shortUrl
    @GetMapping("/analytics/getCountOf/{sortUrl}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ClickEventDto>> getSortUrlCount(@PathVariable String sortUrl
                                                                , @RequestParam("startDate") String startDate
                                                                , @RequestParam("endDate") String endDate){

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start=LocalDateTime.parse(startDate,dateTimeFormatter);
        LocalDateTime end=LocalDateTime.parse(endDate,dateTimeFormatter);
      List<ClickEventDto> clickEventDtos=  urlMappingService.getClickEventsByDate(sortUrl,start,end);
      return ResponseEntity.ok(clickEventDtos);
    }

    //controller for get all the click of a user in between some dat

    @GetMapping("/analytics/getAllCounts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<LocalDate,Long>> getSortUrlCount(Principal principal
                                                                   , @RequestParam("startDate") String startDate
                                                                   , @RequestParam("endDate") String endDate ){
        User user=userService.findByUserName(principal.getName());

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start= LocalDate.parse(startDate,dateTimeFormatter);
        LocalDate end=LocalDate.parse(endDate,dateTimeFormatter);
        Map<LocalDate,Long> allClickEventOfUser=  urlMappingService.getAllClickEventOfUser(user,start,end);
        return ResponseEntity.ok(allClickEventOfUser);
    }

}
