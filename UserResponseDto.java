package com.LoginSignUp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("password")
public class UserResponseDto {
    private String id;
    private String username;
    private String password;

    @JsonIgnore
    private String email;
}
