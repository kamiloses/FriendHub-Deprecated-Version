package com.application.friendhub.controller;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.mainSide.TimelineEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.ProfileDto;
import com.application.friendhub.service.FriendsService;
import com.application.friendhub.service.ProfileDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final UserDetailsRepository userDetailsRepository;

    private final ProfileDtoService profileDtoService;

    private final UserRepository userRepository;
    private final FriendsService friendsService;

    public HomeController(UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService, UserRepository userRepository, FriendsService friendsService) {
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
        this.userRepository = userRepository;
        this.friendsService = friendsService;
    }


    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TimelineEntity> allPosts = userRepository.findAllTimelineEntityByEmail(email);
        model.addAttribute("allPosts", allPosts);
        return "html/mainPaige";
    }

   /* @PostMapping("/home/add")
    public String addPostOnHomeSide(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        model.addAttribute("userDetails", userDetails);

        return "redirect:/html/mainPaige";
    }*/





    @GetMapping("/friendhub/friends")
    public String friends() {


        return "html/friendsPaige";
    }

    @GetMapping("/friendhub/profile")
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);

        model.addAttribute("userDetailsEntity", userDetailsEntity);
    /*UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);
    userDetailsRepository.save(userDetailsEntity);*/


        return "html/ProfilePage";
    }

    @PostMapping("/friendhub/profile/add")
    public String modifyProfile(ProfileDto profileDto) {
        UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);
        userDetailsRepository.save(userDetailsEntity);


        return "redirect:/friendhub/profile";
    }


    @GetMapping("/friendhub/searchFriends")
    public String searchFriends(Model model,  String fullName) {
   friendsService.findUserByNameOrSurname(model,fullName);





        return "html/searchFriends";


    }

    @PostMapping("/friendhub/searchFriends/inviteFriend")
   public String addFriend(ProfileDto profileDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FriendsListEntity> possessedFriends = userRepository.findAllFriendListEntityByEmail(name);


    return "redirect:/friendhub/searchFriends";}





}