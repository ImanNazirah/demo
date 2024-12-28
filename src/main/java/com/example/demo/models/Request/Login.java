package com.example.demo.models.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Login {

    private String email;
    private String username;
    private String password;

}
