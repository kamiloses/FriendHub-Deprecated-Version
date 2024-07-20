package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.loggedUser.dto.UserDetailsDto;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsDtoService {

    private UserDetailsDto userDetailsDto;



    public List<UserDetailsDto> userDetailsEntityToDtoList(List<UserDetailsEntity> userDetailsEntities) {
    return userDetailsEntities.stream().map(userDetailsEntity -> {
         userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userDetailsEntity.getId());
        userDetailsDto.setFirstName(userDetailsEntity.getFirstName());
        userDetailsDto.setLastName(userDetailsEntity.getLastName());
        userDetailsDto.setDate(userDetailsEntity.getDate());
        userDetailsDto.setLocalization(userDetailsEntity.getLocalization());
        userDetailsDto.setEducation(userDetailsEntity.getEducation());
        userDetailsDto.setWork(userDetailsEntity.getWork());
        userDetailsDto.setSex(userDetailsEntity.getSex());
        userDetailsDto.setInterests(userDetailsEntity.getInterests());
        String encodedPicture = Base64.getEncoder().encodeToString(userDetailsEntity.getProfilePicture());
        userDetailsDto.setEncodedProfilePicture(encodedPicture);
        userDetailsDto.setUserEntity(userDetailsEntity.getUserEntity());
        return userDetailsDto;
    }).collect(Collectors.toList());
    }
    public UserDetailsDto userDetailsEntityToDto(UserDetailsEntity userDetailsEntity) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userDetailsEntity.getId());
        userDetailsDto.setFirstName(userDetailsEntity.getFirstName());
        userDetailsDto.setLastName(userDetailsEntity.getLastName());
        userDetailsDto.setDate(userDetailsEntity.getDate());
        userDetailsDto.setLocalization(userDetailsEntity.getLocalization());
        userDetailsDto.setEducation(userDetailsEntity.getEducation());
        userDetailsDto.setWork(userDetailsEntity.getWork());
        userDetailsDto.setSex(userDetailsEntity.getSex());
        userDetailsDto.setInterests(userDetailsEntity.getInterests());
        String encodedPicture = Base64.getEncoder().encodeToString(userDetailsEntity.getProfilePicture());
        userDetailsDto.setEncodedProfilePicture(encodedPicture);
        userDetailsDto.setUserEntity(userDetailsEntity.getUserEntity());
        return userDetailsDto;
    }
    }




