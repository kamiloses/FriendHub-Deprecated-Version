package com.application.friendhub.registrationProcess.service;

import com.application.friendhub.registrationProcess.dto.FirstStepDto;
import com.application.friendhub.registrationProcess.other.Months;
import com.application.friendhub.registrationProcess.other.SelectOption;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SelectDateOfBirthOptionService {

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


        for (Months allMonthsName : allMonthsNames) {
            months.add(new SelectOption(allMonthsName.toString().toLowerCase()));
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


    public boolean isALeapYear(FirstStepDto userDetailsDto) {
        int year = userDetailsDto.getDate().getYear();

        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }


    public int maxDayOfMonth(FirstStepDto userDetailsDto) {

        Months change = changeToEnumMonth(userDetailsDto);
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
            if (isALeapYear(userDetailsDto)) {
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

    private Months changeToEnumMonth(FirstStepDto userDetailsDto) {
        String months = String.valueOf(userDetailsDto.getDate().getMonth());
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


    protected   String monthToNumber(String monthName) {
        if (monthName == null) {
            return null;
        }

        String normalizedMonth = monthName.trim().toLowerCase();

        switch (normalizedMonth) {
            case "january":
                return "1";
            case "february":
                return "2";
            case "march":
                return "3";
            case "april":
                return "4";
            case "may":
                return "5";
            case "june":
                return "6";
            case "july":
                return "7";
            case "august":
                return "8";
            case "september":
                return "9";
            case "october":
                return "10";
            case "november":
                return "11";
            case "december":
                return "12";
            default:
                return null;
        }
    }

public boolean validateDayOfMonth(FirstStepDto userDetailsDto){
   return userDetailsDto.getDate().getDay() <=maxDayOfMonth(userDetailsDto);}



}









