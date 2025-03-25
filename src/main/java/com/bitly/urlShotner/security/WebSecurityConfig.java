package com.bitly.urlShotner.security;

import com.bitly.urlShotner.security.jwt.JwtAuthFilter;
import com.bitly.urlShotner.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor //by this also we can inject the dependency
//this  is known as constructor injection..
// in this class we define the application security like what are the protected endpoint and what are free
//like authenticate the api call.
public class WebSecurityConfig {


    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
 return  new BCryptPasswordEncoder();
    } //for encoding the password

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    //have to create security chain like define what are the auth api rout and what are the free..

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)  //here we protect the application like what are the non authenticated endpoint and what are authenticated.
                .authorizeHttpRequests(auth->auth
                        //problem in this side....
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/auth/user/register").permitAll()
                        .requestMatchers("/auth/user/login").permitAll()
                        .requestMatchers("/{sortUrl}").permitAll()
                        .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        //here we  mention  when the jwt auth filter run or execute or check the header's jwt it valid or not.

//        i think there are some problem in this line or
  http.addFilterBefore(jwtAuthFilter(),UsernamePasswordAuthenticationFilter.class);
  return http.build();
    }



}
