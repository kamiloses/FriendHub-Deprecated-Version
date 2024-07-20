package com.application.friendhub.registrationProcess.other;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class DateOfBirth {



    @Column(name = "day_of_birth")
    private int day;

    @Column(name = "month_of_birth")
    private int month;

    @Column(name = "year_of_birth")
    private int year;

}
