package com.bitly.urlShotner.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//this works like a middelware like check eatch request have the token
//by this for every request there is a single execution happens.
//if the req are authenticated. how the spring is know when to run this for that  we make some config in websecurity config.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth/user/register") || path.startsWith("/auth/user/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //there is a flow how to ckeck the token is valid or not
            //1-->get the Jwt token from header
  String jwtToken=jwtUtils.getJwtFromHeader(request);
            //2-->check if it valid or not

  if(jwtToken!=null && jwtUtils.validateToken(jwtToken)){
      //3-->find the user from the token
      String username=jwtUtils.getUsernameFromJwtToken(jwtToken);
      UserDetails userDetails=userDetailsService.loadUserByUsername(username); // we directly fetch the userDetails from spring security.
      if(userDetails !=null){  //this means the user is authenticated.
          UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          //as we know at this point the user is authenticated so  we store the  authenticated user inside the spring security context.
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }
  }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);//it works like a next function work in node js
    }
}
