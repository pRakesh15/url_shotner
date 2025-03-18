package com.bitly.urlShotner.security.jwt;

import com.bitly.urlShotner.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expTimeMs}")
    private int jwtExpTimeinMs;


    //create a method for create Token using UserDetails.
    //it will use when we login a user.
    public String generateToken(UserDetailsImpl userDetails){
        String username=userDetails.getUsername();
        String roles=userDetails.getAuthorities().stream()
                .map(authority->authority.getAuthority()).collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("role",roles)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+jwtExpTimeinMs)))
                .signWith(key())
                .compact();
    }


    // create a method for extracting the jwt token from the header.
    //it will use when a authenticated enpoint is heat by the server with some headers
    public  String getJwtFromHeader(HttpServletRequest request){
        String brearerToken=request.getHeader("Authorization");
        //check if token is present or not if present then valid or not
        if(brearerToken != null && brearerToken.startsWith("Bearer ")){
            return brearerToken.substring(7);
        }
        return "No token found";
    }

    //create a method for validate the toke like the given token is validate or not.
    public boolean validateToken(String authToken){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e){
            throw  new RuntimeException(e);
        } catch (Exception e){
            throw  new RuntimeException(e);
        }
    }


    //create a method to extract the user name from the token
    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    //by this method we create a token by base64 algo.
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }



}
