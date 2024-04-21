package com.application.friendhub.dto;

import jakarta.persistence.JoinColumn;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DateOfBirth {


    @JoinColumn(name = "DayOfBirth")
    private int day;
    @JoinColumn(name = "MonthOfBirth")
    private int month;
    @JoinColumn(name = "YearOfBirth")
    private int year;

}
