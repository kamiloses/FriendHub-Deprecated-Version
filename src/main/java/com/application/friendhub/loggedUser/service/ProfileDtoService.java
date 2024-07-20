package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProfileDtoService {
    private final UserDetailsRepository userDetailsRepository;


    public ProfileDtoService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    public UserDetailsEntity profileDtoToUserDetailsEntity(ProfileDto profileDto) throws IOException {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(name);
        userDetailsEntity.setFirstName(profileDto.getFirstName());
        userDetailsEntity.setLastName(profileDto.getLastName());
//        userDetailsEntity.setDate(profileDto.getDate());
        userDetailsEntity.setLocalization(profileDto.getLocalization());
        userDetailsEntity.setEducation(profileDto.getEducation());
        userDetailsEntity.setWork(profileDto.getWork());
        userDetailsEntity.setSex(profileDto.getSex());
        userDetailsEntity.setInterests(profileDto.getInterests());
        userDetailsEntity.setProfilePicture(profileDto.getProfilePicture().getBytes());



        return userDetailsEntity;}


    public ProfileDto userDetailsEntityToProfileDto(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(name);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFirstName(userDetailsEntity.getFirstName());
        profileDto.setLastName(userDetailsEntity.getLastName());
//        profileDto.setDate(userDetailsEntity.getDate());
        profileDto.setLocalization(userDetailsEntity.getLocalization());
        profileDto.setEducation(userDetailsEntity.getEducation());
//        profileDto.setSex(userDetailsEntity.getSex());
        /*profileDto.setProfilePicture(userDetailsEntity.getProfilePicture());*/
        return profileDto;}




}











