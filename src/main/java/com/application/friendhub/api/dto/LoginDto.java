package com.application.friendhub.api.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}