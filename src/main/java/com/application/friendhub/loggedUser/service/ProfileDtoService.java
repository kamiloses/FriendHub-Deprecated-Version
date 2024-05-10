package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileDtoService {
    private UserDetailsRepository userDetailsRepository;


    public ProfileDtoService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    public UserDetailsEntity profileDtoToUserDetailsEntity(ProfileDto profileDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(name);
        userDetailsEntity.setFirstName(profileDto.getFirstName());
        userDetailsEntity.setLastName(profileDto.getLastName());
//        userDetailsEntity.setDate(profileDto.getDate());
        userDetailsEntity.setLocalization(profileDto.getLocalization());
        userDetailsEntity.setEducation(profileDto.getEducation());
        userDetailsEntity.setWork(profileDto.getWork());
//        userDetailsEntity.setSex(profileDto.getSex());
        userDetailsEntity.setInterests(profileDto.getInterests());


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
    return profileDto;}




}











