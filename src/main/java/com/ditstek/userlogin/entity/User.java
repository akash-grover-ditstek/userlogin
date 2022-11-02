package com.ditstek.userlogin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private String mobileNo;
    private String userName;
    private String password;
    private String email;
}
