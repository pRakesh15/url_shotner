package com.bitly.urlShotner.service;


import com.bitly.urlShotner.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


//as we know spring provide us own inbuilt implementation of UserDetails and this is a interface
//we cant directly access the interface so we create a class  and implement the interface.
//when user is load from the database by the help of UserDetailsServiceImpl the UserDetailsImpl class allow the spring security to authenticate the user,
//and it stores the user authentication details like username password and role etc.
//it internally  authenticates the user and role-base access control.
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID=1L;  //this is use for serialization

    //store the basic auth details.
    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    //by this method we fetch the user from the database and convert it into a object that is requred by the security.

    public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //when we find a user and pass to the build method the user is store in spring security  bu this method.
    //and understand by the spring security.

    public  static  UserDetailsImpl build(User user){
        GrantedAuthority authority=new SimpleGrantedAuthority(user.getRole());
        ///it will return the authenticate user to serviceimple class
        return  new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }

    //NOTE---->when we will login with some credential Spring Security uses the UserDetailsImpl object to authenticate the user

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
