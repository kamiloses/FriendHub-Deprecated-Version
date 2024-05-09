package com.application.friendhub.controller;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.ProfileDto;
import com.application.friendhub.dto.TimelineDto;
import com.application.friendhub.service.FriendsService;
import com.application.friendhub.service.ProfileDtoService;
import com.application.friendhub.service.TimelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class HomeController {


      private final TimelineService timelineService;
    private final TimelineRepository timelineRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final ProfileDtoService profileDtoService;

    private final UserRepository userRepository;
    private final FriendsService friendsService;

    public HomeController(TimelineService timelineService, TimelineRepository timelineRepository, UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService, UserRepository userRepository, FriendsService friendsService) {
        this.timelineService = timelineService;
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
        this.userRepository = userRepository;
        this.friendsService = friendsService;
        this.timelineRepository = timelineRepository;
    }


    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TimelineEntity> allPosts = userRepository.findAllTimelineEntityByEmail(email);
        model.addAttribute("allPosts", allPosts);
        return "html/mainPaige";
    }

   @PostMapping("/home/add")
    public String addPostOnHomeSide(@ModelAttribute TimelineDto timelineDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
       TimelineEntity timelineEntity = timelineService.timelineDtoToEntity(timelineDto);
       timelineRepository.save(timelineEntity);
       log.error("saved");



        return "redirect:/home";
    }





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