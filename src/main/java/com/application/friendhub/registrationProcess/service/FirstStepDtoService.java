package com.application.friendhub.registrationProcess.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.api.other.Role;
import com.application.friendhub.registrationProcess.dto.FirstStepDto;
import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FirstStepDtoService {

    private final SelectDateOfBirthOptionService selectOptionService;

    private Sex sex=null;

    public FirstStepDtoService(SelectDateOfBirthOptionService selectOptionService) {
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
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setCreatedAt(new Date());

        sex=firstStepDto.getSex();


        return userEntity;
    }



    public UserDetailsEntity convertFirstStepDtoToUserDetailsEntity(FirstStepDto firstStepDto, UserEntity user) throws IOException {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setFirstName(firstStepDto.getFirstName());
        userDetailsEntity.setLastName(firstStepDto.getLastName());
        userDetailsEntity.setDate(new DateOfBirth(firstStepDto.getDate().getDay(), firstStepDto.getDate().getMonth(), firstStepDto.getDate().getYear()));
      userDetailsEntity.setSex(sex);
        userDetailsEntity.setUserEntity(user);
        if (sex==Sex.FEMALE){
            //dla dockera zostaw to "/app/resources/templates/html/facebookAvatarFemale.png"
            Path path = Paths.get("C:\\Users\\kamil\\IdeaProjects\\FriendHub\\src\\main\\resources\\static\\img\\facebookAvatarFemale.png");
            byte[] bytes = Files.readAllBytes(path);
            userDetailsEntity.setProfilePicture(bytes);

        }else {

            Path path = Paths.get("C:\\Users\\kamil\\IdeaProjects\\FriendHub\\src\\main\\resources\\static\\img\\facebookAvatarMale.png");
            byte[] bytes = Files.readAllBytes(path);
            userDetailsEntity.setProfilePicture(bytes);

        }

        //todo zmień potem tą nazwe getYear itp


        return userDetailsEntity;
    }


}




