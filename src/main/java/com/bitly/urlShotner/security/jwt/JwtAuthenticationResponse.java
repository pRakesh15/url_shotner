package com.bitly.urlShotner.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

//basically this is a DTO class that is define   how the authentication response lokes like.
@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
}
