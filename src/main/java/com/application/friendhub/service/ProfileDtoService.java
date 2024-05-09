package com.application.friendhub.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.dto.ProfileDto;
import org.springframework.security.core.Authentication;
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
        userDetailsEntity.setDate(profileDto.getDate());
        userDetailsEntity.setNationality(profileDto.getNationality());
        userDetailsEntity.setEducation(profileDto.getEducation());
        userDetailsEntity.setSex(profileDto.getSex());


        return userDetailsEntity;}


    public ProfileDto userDetailsEntityToProfileDto(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(name);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFirstName(userDetailsEntity.getFirstName());
        profileDto.setLastName(userDetailsEntity.getLastName());
        profileDto.setDate(userDetailsEntity.getDate());
        profileDto.setNationality(userDetailsEntity.getNationality());
        profileDto.setEducation(userDetailsEntity.getEducation());
        profileDto.setSex(userDetailsEntity.getSex());
    return profileDto;}




}











