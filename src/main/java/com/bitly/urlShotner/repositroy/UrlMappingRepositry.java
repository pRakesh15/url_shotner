package com.bitly.urlShotner.repositroy;

import com.bitly.urlShotner.models.UrlMapping;
import com.bitly.urlShotner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepositry extends JpaRepository<UrlMapping,Long> {
    UrlMapping findBySortUrl(String sortUrl);
    List<UrlMapping> findByUser(User user);
}
