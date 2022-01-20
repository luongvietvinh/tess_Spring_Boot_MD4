package com.example.demoSpringBoot_MD4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "user_name ko duoc de trong")
    @Size(min = 4,max = 9,message = "min = 4 && max = 9")
    private String userName;
    @NotEmpty(message = "password ko duoc de trong")
    private String password;
    @NotEmpty(message = "phoneNumber ko duoc de trong")
    private String phoneNumber;
    @NotEmpty(message = "email ko duoc de trong")
    private String email;
    private String img;
    @ManyToOne
    private Role role;
}
