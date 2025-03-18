package com.bitly.urlShotner.service;

import com.bitly.urlShotner.dto.LoginRequest;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.UserRepository;
import com.bitly.urlShotner.security.jwt.JwtAuthenticationResponse;
import com.bitly.urlShotner.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                //here we 1st authenticate the user by username and password
new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        //if it returns true then we store the user info inside the contexxtholder of springSecurity.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //find the userdetails by  userDetailsImpl we made
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
       //according to the user we generate the token..
        String jwt=jwtUtils.generateToken(userDetails);
    return new JwtAuthenticationResponse(jwt);
    }


    public User findByUserName(String username){
        return  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with the username "+username));
    }
}
