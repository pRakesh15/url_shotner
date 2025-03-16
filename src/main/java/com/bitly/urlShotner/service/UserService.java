package com.bitly.urlShotner.service;

import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
   private UserRepository userRepository;

    public User registerUser(User user){

         user.setPassword(passwordEncoder.encode(user.getPassword()));
  return userRepository.save(user);

    }
}
