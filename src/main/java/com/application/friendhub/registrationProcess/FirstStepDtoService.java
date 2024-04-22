package com.application.friendhub.registrationProcess;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.DateOfBirth;
import com.application.friendhub.fronted.SelectOptionService;
import org.springframework.stereotype.Service;

@Service
public class FirstStepDtoService {

    private final SelectOptionService selectOptionService;


    public FirstStepDtoService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;

    }

    public void SetMonthForFirstStepDto(String day, String month, String year, FirstStepDto firstStepDto) {
        int intDay = Integer.parseInt(day);
        int intMonth = Integer.parseInt(selectOptionService.monthToNumber(month));
        int intYear = Integer.parseInt(year);

        DateOfBirth dateOfBirth = new DateOfBirth(intDay, intMonth, intYear);

        firstStepDto.setDate(dateOfBirth);


    }


    public UserEntity convertFirstStepDtoToUserEntity(FirstStepDto firstStepDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(firstStepDto.getEmail());
        userEntity.setPassword(firstStepDto.getPassword());


        return userEntity;
    }

    public UserDetailsEntity convertFirstStepDtoToUserDetailsEntity(FirstStepDto firstStepDto, UserEntity user) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setFirstName(firstStepDto.getFirstName());
        userDetailsEntity.setLastName(firstStepDto.getLastName());
        userDetailsEntity.setDate(new DateOfBirth(firstStepDto.getDate().getDay(), firstStepDto.getDate().getMonth(), firstStepDto.getDate().getYear()));
        userDetailsEntity.setUserEntity(user);
        //todo zmień potem tą nazwe getYear itp
//        userDetailsEntity.setSex(firstStepDto.getSex());
//todo pamiętaj by ustawić jeszcze opcje dla płci

        return userDetailsEntity;
    }


}




