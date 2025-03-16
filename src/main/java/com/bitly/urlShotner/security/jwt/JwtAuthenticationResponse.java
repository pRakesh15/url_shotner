package com.bitly.urlShotner.security.jwt;

import lombok.Data;

//basically this is a DTO class that is define   how the authentication response lokes like.
@Data
public class JwtAuthenticationResponse {
    private String token;
}
