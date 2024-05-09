package com.application.friendhub.registrationProcess;

import com.application.friendhub.dto.DateOfBirth;
import com.application.friendhub.dto.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FirstStepDto {


    @Size(min = 2, max = 30, message = "the first name field should have between 2 and 30 letters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "the first name field should contain only letters ")
    private String firstName;

    //    @Size(min = 2, max = 30, message = "the last name field should have between 2 and 30 letters")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "the first name field should contain only letters and have between 3 and 15 letters")

    private String lastName;

    @Email(message = "please fulfill  the email field")
    private String email;


    @Size(min = 8, max = 30, message = "the password field should have between 8 and 30 letters")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
//            message = "the password field should contain at least  one lower Case letter,one upper case letter, one digit and one special character")
    private String password;

    private Role role;

    private DateOfBirth date;

    /*private Sex sex;*/



}