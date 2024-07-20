package com.application.friendhub.Entity;

import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "profilePicture")
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


    @Lob
//    @Basic(fetch = FetchType.LAZY)
    @Column(name = "profile_picture", columnDefinition = "LongBlob")

    private byte[] profilePicture;


    @OneToOne
    private UserEntity userEntity;


}
