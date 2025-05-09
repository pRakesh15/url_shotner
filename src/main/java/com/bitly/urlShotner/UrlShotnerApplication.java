package com.bitly.urlShotner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class   UrlShotnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShotnerApplication.class, args);
	}

}
