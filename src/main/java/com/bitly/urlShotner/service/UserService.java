package com.bitly.urlShotner.service;

import com.bitly.urlShotner.dtos.LoginRequest;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.UserRepository;
import com.bitly.urlShotner.security.jwt.JwtAuthenticationResponse;
import com.bitly.urlShotner.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
   private UserRepository userRepository;
   private JwtUtils jwtUtils;


   private AuthenticationManager authenticationManager;


    public User registerUser(User user){

         user.setPassword(passwordEncoder.encode(user.getPassword()));
  return userRepository.save(user);

    }

    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(
new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        String jwt=jwtUtils.generateToken(userDetails);
    return new JwtAuthenticationResponse(jwt);
    }
}
