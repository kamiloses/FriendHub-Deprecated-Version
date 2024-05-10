package com.application.friendhub.Entity;

import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import jakarta.persistence.*;
import lombok.*;

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

    private String localization;

    private String Education;

    private String work;

    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    private String interests;


    @OneToOne
    private UserEntity userEntity;


}
