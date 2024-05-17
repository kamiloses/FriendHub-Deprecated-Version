package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.*;
import com.application.friendhub.dto.LikeDto;
import com.application.friendhub.dto.TimelineDto;
import com.application.friendhub.loggedUser.dto.CommentDto;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import com.application.friendhub.loggedUser.service.*;
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

    private final CommentsRepository commentsRepository;
    private final TimelineService timelineService;
    private final TimelineRepository timelineRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final ProfileDtoService profileDtoService;

    private final UserRepository userRepository;
    private final FriendsService friendsService;

    private final AddFriendsService addFriendsService;
    private FriendsListRepository friendsListRepository;

    private CommentService commentService;


    public HomeController(CommentsRepository commentsRepository, TimelineService timelineService, TimelineRepository timelineRepository, UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService, UserRepository userRepository, FriendsService friendsService, AddFriendsService addFriendsService, FriendsListRepository friendsListRepository, CommentService commentService) {
        this.commentsRepository = commentsRepository;
        this.timelineService = timelineService;
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
        this.userRepository = userRepository;
        this.friendsService = friendsService;
        this.timelineRepository = timelineRepository;
        this.addFriendsService = addFriendsService;
        this.friendsListRepository = friendsListRepository;
        this.commentService = commentService;
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
        TimelineEntity timelineEntity = timelineService.addPostEntityToDto(timelineDto);

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
    public String searchedProfile(@RequestParam String firstName,@RequestParam String lastName, @RequestParam Long id, Model model) {
       List<TimelineEntity> posts=timelineRepository.findTimelineEntityByUser_Id(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));
        List<FriendsListEntity> user = userRepository.findAllFriendListEntityById(userEntity.getId());

       model.addAttribute("allFriends", user);


        List<TimelineEntity> postss = timelineRepository.findTimelineEntitiesWithMatchingAuthor(firstName, lastName);
        model.addAttribute("allPosts",posts);

        return "html/somebodysProfile";
    }

    @PostMapping("/home/removePost")//todo zmien na delete mapping
    public String removePost(@ModelAttribute TimelineDto timelineDto){
        timelineRepository.deleteById(timelineDto.getId());


    return "redirect:/home";}



/*    @PostMapping("/addComment")//todo zmień endpoint
    public String addComment(@ModelAttribute CommentDto commentDto){







return null;}*/
@PostMapping("/friendhub/upload")
public String upload(@ModelAttribute TimelineDto timelineDto){
    TimelineEntity savedEntity = timelineRepository.findById(timelineDto.getId()).orElseThrow(() -> new UsernameNotFoundException("not found"));
    timelineRepository.save(timelineService.timelineDtoToEntity(timelineDto));



    return "redirect:/profile"+"?firstName=" + savedEntity.getUser().getUserDetailsEntity().getFirstName() +
            "&lastName=" + savedEntity.getUser().getUserDetailsEntity().getLastName() +
            "&id=" + savedEntity.getUser().getId();}


@PostMapping("/home/addComment")//todo zamien urk
    public String addComment(@ModelAttribute CommentDto commentDto) {

    CommentsEntity commentsEntity = commentService.commentDtoToEntity(commentDto);
    commentsRepository.save(commentsEntity);



return "redirect:/home";
}

@PostMapping("/home/addLike")
    public String addLike(@ModelAttribute LikeDto likeDto) {



return "redirect:/home";}




}




