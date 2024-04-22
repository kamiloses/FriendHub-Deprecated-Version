package com.application.friendhub.fronted;

import com.application.friendhub.dto.DateOfBirth;
import com.application.friendhub.registrationProcess.FirstStepDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelectOptionServiceTest {
    @InjectMocks
    private SelectOptionService selectOptionService;

    @Mock
    private FirstStepDto userDetailsDto;


    @ParameterizedTest
    @CsvFileSource(resources = "/testFiles/SelectOptionService/validateDayOfMonthTrue",numLinesToSkip = 1)
    void validateDayOfMonth1(int day,int month,int year) {
        Mockito.when(userDetailsDto.getDate()).thenReturn(new DateOfBirth(day,month,year));


        boolean validation = selectOptionService.validateDayOfMonth(userDetailsDto);

        Assertions.assertTrue(validation);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testFiles/SelectOptionService/validateDayOfMonthFalse",numLinesToSkip = 1)
    void validateDayOfMonth2(int day,int month,int year) {
        Mockito.when(userDetailsDto.getDate()).thenReturn(new DateOfBirth(day,month,year));


        boolean validation = selectOptionService.validateDayOfMonth(userDetailsDto);

        Assertions.assertFalse(validation);

    }

}