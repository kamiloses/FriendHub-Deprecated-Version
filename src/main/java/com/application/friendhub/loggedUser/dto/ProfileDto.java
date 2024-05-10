package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Data
public class ProfileDto {


    private String firstName;
    private String lastName;
    private DateOfBirth date;
    private String localization;
    private String Education;
    private String work;
    private Sex sex;
    private String interests;


}