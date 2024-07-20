package com.application.friendhub.loggedUser.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
@Service
public class DateConverterService implements Converter<LocalDate, Date> {


    @Override
    public Date convert(LocalDate source) {

        DefaultConversionService defaultConversionService = new DefaultConversionService();

        return defaultConversionService.convert(source, Date.class);
    }
}
