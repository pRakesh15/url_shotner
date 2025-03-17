package com.bitly.urlShotner.service;

import com.bitly.urlShotner.models.User;
import com.bitly.urlShotner.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//this class helps us to load the user by username from the database.
//create a bridge for user of database to the user of SpringSecurity.
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user note found with name "+username));
//when by the helpof UserDetailsServiceImpl class we find the user from the database we will pass the user to the impl class to authenticate the user.
        //when we login  by the class spring 1st find the user and then pass  to the impl class.
        return UserDetailsImpl.build(user);
        ///it will return the authenticate user to serviceimple class
    }
}
