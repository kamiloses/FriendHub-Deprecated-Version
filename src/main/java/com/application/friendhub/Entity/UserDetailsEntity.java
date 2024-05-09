package com.application.friendhub.Entity;

import com.application.friendhub.dto.DateOfBirth;
import com.application.friendhub.dto.Sex;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;

    @Embedded
    private DateOfBirth date;

    private Locale nationality;

    private String Education;

    @Enumerated(EnumType.STRING)
    private Sex sex;


    @OneToOne
    private UserEntity userEntity;


}
