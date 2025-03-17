package com.bitly.urlShotner.controller;

import com.bitly.urlShotner.dtos.RegisterRequest;
import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setRole("ROLE_USER");
        user.setPassword(registerRequest.getPassword());

        userService.registerUser(user);

        return ResponseEntity.ok("User created successfully");
    }
}
