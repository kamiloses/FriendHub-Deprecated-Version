package com.application.friendhub.api.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public JwtResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}