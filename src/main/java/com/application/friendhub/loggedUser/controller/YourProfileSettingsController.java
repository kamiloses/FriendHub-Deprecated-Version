package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import com.application.friendhub.loggedUser.service.ProfileDtoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class YourProfileSettingsController{
private final UserDetailsRepository userDetailsRepository;
private final ProfileDtoService profileDtoService;
    public YourProfileSettingsController(UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService) {
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
    }



    @GetMapping("/friendHub/profile")
public String profile(Model model) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);

    model.addAttribute("userDetailsEntity", userDetailsEntity);



    return "html/loggedUser/ProfileSettingsPage";
}




@PostMapping("/friendHub/profile/modifyProfile")
public String modifyProfile(ProfileDto profileDto) throws IOException {
    UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);


    userDetailsRepository.save(userDetailsEntity);


    return "redirect:/friendHub/profile";
}





}
