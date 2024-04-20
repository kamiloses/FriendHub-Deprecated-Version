package com.application.friendhub.fronted;

import com.application.friendhub.dto.FirstStepDto;
import com.application.friendhub.dto.Months;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SelectOptionService {

    private List<SelectOption> days;
    private List<SelectOption> months;
    private List<SelectOption> years;


    public List<SelectOption> getYears() {

        years = new ArrayList<>();
        for (int i = 1910; i <= LocalDate.now().getYear(); i++) {
            years.add(new SelectOption(String.valueOf(i)));


        }
        return years;
    }


    public List<SelectOption> getMonths() {
        months = new ArrayList<>();
        Months[] allMonthsNames = Months.values();


        for (int i = 0; i < allMonthsNames.length; i++) {
            months.add(new SelectOption(allMonthsNames[i].toString().toLowerCase()));
        }


        return months;
    }


    public List<SelectOption> getDays() {
        days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(new SelectOption(String.valueOf(i)));
        }
        return days;
    }


    public boolean isALeapYear(FirstStepDto firstStepDto) {
        int year = firstStepDto.getDate().getYear();

        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }


    public int maxDayOfMonth(FirstStepDto firstStepDto) {

        Months change = change(firstStepDto);
        int maxNumberOfMonth;

        switch (change) {
            case JANUARY:
            case MARCH:
            case MAY:
            case JULY:
            case AUGUST:
            case OCTOBER:
            case DECEMBER:
                maxNumberOfMonth = 31;
                break;

            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                maxNumberOfMonth = 30;
                break;

            case FEBRUARY:
            if (isALeapYear(firstStepDto)) {
                maxNumberOfMonth = 29;
            } else {
                maxNumberOfMonth = 28;
            }
            break;

            default:
                throw new IllegalArgumentException("the month is incorrect");


        }


        return maxNumberOfMonth;
    }

    private Months change(FirstStepDto firstStepDto) {
        String months = String.valueOf(firstStepDto.getDate().getMonth());
        switch (months) {
            case "1":
                return Months.JANUARY;
            case "2":
                return Months.FEBRUARY;
            case "3":
                return Months.MARCH;
            case "4":
                return Months.APRIL;
            case "5":
                return Months.MAY;
            case "6":
                return Months.JUNE;
            case "7":
                return Months.JULY;
            case "8":
                return Months.AUGUST;
            case "9":
                return Months.SEPTEMBER;
            case "10":
                return Months.OCTOBER;
            case "11":
                return Months.NOVEMBER;
            case "12":
                return Months.DECEMBER;
        }


        return null;
    }
}









