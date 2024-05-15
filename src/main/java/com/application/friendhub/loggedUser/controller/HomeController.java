package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.FriendsListRepository;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.TimelineDto;
import com.application.friendhub.loggedUser.dto.CommentDto;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import com.application.friendhub.loggedUser.service.AddFriendsService;
import com.application.friendhub.loggedUser.service.FriendsService;
import com.application.friendhub.loggedUser.service.ProfileDtoService;
import com.application.friendhub.loggedUser.service.TimelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class HomeController {


    private final TimelineService timelineService;
    private final TimelineRepository timelineRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final ProfileDtoService profileDtoService;

    private final UserRepository userRepository;
    private final FriendsService friendsService;

    private final AddFriendsService addFriendsService;
    private FriendsListRepository friendsListRepository;

    public HomeController(TimelineService timelineService, TimelineRepository timelineRepository, UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService, UserRepository userRepository, FriendsService friendsService, AddFriendsService addFriendsService, FriendsListRepository friendsListRepository) {
        this.timelineService = timelineService;
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
        this.userRepository = userRepository;
        this.friendsService = friendsService;
        this.timelineRepository = timelineRepository;
        this.addFriendsService = addFriendsService;
        this.friendsListRepository = friendsListRepository;
    }


    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));
        List<TimelineEntity> allPosts = userRepository.findAllTimelineEntityByEmail(email);
        UserDetailsEntity userDetails = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);
        List<FriendsListEntity> user = userRepository.findAllFriendListEntityById(userEntity.getId());
        /*List<CommentsEntity> commentsE = timelineRepository.findCommentsE(allPosts);*/

        model.addAttribute("allPosts", allPosts);
        model.addAttribute("userDetails", userDetails);

        model.addAttribute("allFriends", user);

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

  /*  @PostMapping("friendHub/home/delete")
    public String home(UserDto){
        u


    return "redirect:/home";}
*/







    @PostMapping("/friendhub/profile/modifyProfile")
    public String modifyProfile(ProfileDto profileDto) {
        UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);
        userDetailsRepository.save(userDetailsEntity);


        return "redirect:/friendhub/profile";
    }


    @GetMapping("/friendhub/searchFriends")
    public String searchFriends(Model model, String fullName) {
        List<UserDetailsEntity> user = friendsService.findUserByNameOrSurname(fullName);

        model.addAttribute("foundUsers", user);


        return "html/searchFriends";


    }

    @PostMapping("/friendhub/searchFriends/searchAll")
    public String searchFriend(ProfileDto profileDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();


        return "redirect:/friendhub/searchFriends";
    }


    @PostMapping("/friendhub/searchFriends/add")
    public String addFriend(@ModelAttribute FriendsDto friendsDto) {

        FriendsListEntity invitedAccount = addFriendsService.invitedFriendsDtoToEntity(friendsDto);
        FriendsListEntity invitingAccount = addFriendsService.invitingFriendsDtoToEntity(friendsDto);

        friendsListRepository.save(invitedAccount);
        friendsListRepository.save(invitingAccount);

        return "redirect:/friendhub/searchFriends";
    }


    @GetMapping("/profile")
    public String searchedProfile(@RequestParam String firstName,@RequestParam String lastName, Model model) {
        List<TimelineEntity> posts = timelineRepository.findTimelineEntitiesWithMatchingAuthor(firstName, lastName);
        model.addAttribute("allPosts",posts); //todo zamień na wyszukiwanie poprzez email

        return "html/somebodysProfile";
    }

    @PostMapping("/home/removePost")
    public String removePost(@ModelAttribute TimelineDto timelineDto){
        timelineRepository.deleteById(timelineDto.getId());


    return "redirect:/home";}




/*    @PostMapping("/addComment")//todo zmień endpoint
    public String addComment(@ModelAttribute CommentDto commentDto){







return null;}*/





}
