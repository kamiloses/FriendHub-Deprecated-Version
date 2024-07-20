package com.application.friendhub.registrationProcess.dto;

import com.application.friendhub.api.other.Role;
import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import com.application.friendhub.registrationProcess.other.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FirstStepDto {

//todo zmień walidacje

    @Size(min = 2, max = 30, message = "the first name field should have between 2 and 30 letters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "the first name field should contain only letters ")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "the first name field should contain only letters and have between 3 and 15 letters")

    private String lastName;

    @Email(message = "please fulfill  the email field")
    @UniqueEmail
    @NotBlank(message = "email field must not be null")
    private String email;


    @Size(min = 8, max = 30, message = "the password field should have between 8 and 30 signs")
    private String password;

    private Role role;

    private DateOfBirth date;

    private Sex sex;


}