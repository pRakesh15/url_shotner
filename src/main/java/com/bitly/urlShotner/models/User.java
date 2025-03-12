package com.bitly.urlShotner.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity  //use for define the class as an entity class that is store in the database.
@Data   //using data annotation of loombook for avoiding geter and setters.
@Table(name = "users")  //make the table name ...
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //here we define the id as auto generate with the identity startegy..
    private Long id;
    private String email;
    private String username;
    private String password;
    private  String role="ROLE_USER";
}
