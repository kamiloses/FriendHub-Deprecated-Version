package com.application.friendhub.dto;

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
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private DateOfBirth date;
    private Locale nationality;
    private String Education;

    private String work;

    private Sex sex;




}
