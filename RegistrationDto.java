package com.LoginSignUp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDto {

    private String username;
    private String password;
    private String email;
    }
