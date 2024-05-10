package com.application.friendhub.registrationProcess.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecondStepDto {
      @NotBlank(message = "token should not be blank")
    private  String token;



}
