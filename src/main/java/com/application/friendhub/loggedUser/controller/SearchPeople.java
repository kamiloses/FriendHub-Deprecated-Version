package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.FriendsListRepository;
import com.application.friendhub.Repository.PrivateChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import com.application.friendhub.loggedUser.dto.UserDetailsDto;
import com.application.friendhub.loggedUser.service.AddFriendsService;
import com.application.friendhub.loggedUser.service.PrivateChatService;
import com.application.friendhub.loggedUser.service.SearchingForFriendsService;
import com.application.friendhub.loggedUser.service.UserDetailsDtoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchPeople {

    private final SearchingForFriendsService searchingForFriendsService;
    private final UserDetailsDtoService userDetailsDtoService;
    private final UserRepository userRepository;
    private final AddFriendsService addFriendsService;

    public SearchPeople(SearchingForFriendsService searchingForFriendsService, UserDetailsDtoService userDetailsDtoService, UserRepository userRepository, AddFriendsService addFriendsService) {
        this.searchingForFriendsService = searchingForFriendsService;
        this.userDetailsDtoService = userDetailsDtoService;
        this.userRepository = userRepository;
        this.addFriendsService = addFriendsService;
    }

//todo zoptymalizuj kod
    @GetMapping("/friendHub/searchFriends")
    public String searchPeople(Model model, String fullName) {
        List<UserDetailsEntity> user = searchingForFriendsService.findUserByNameOrSurname(fullName);


        List<UserDetailsDto> userDetailsDto = userDetailsDtoService.userDetailsEntityToDtoList(user);
        model.addAttribute("foundUsers", userDetailsDto);




        List<Long> isHeYourFriend=new ArrayList<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity loggedUser = userRepository.findUserEntityByEmail(name).orElseThrow();

        for (UserDetailsEntity userDetailsEntity : user) {
            for (FriendsListEntity friendsListEntity : loggedUser.getFriendsListEntities()) {
                if (friendsListEntity.getConnectionToYourOwnAccount().getId().equals(userDetailsEntity.getId())) {
                    isHeYourFriend.add(userDetailsEntity.getId());

                }
            }
            model.addAttribute("isHeYourFriend", isHeYourFriend);

        }


        return "html/loggedUser/searchFriends";


    }
    @PostMapping("/friendHub/searchFriends/add")
    public String addToFriend(@ModelAttribute FriendsDto friendsDto) {

       addFriendsService.addFriend(friendsDto);


        return "redirect:/friendHub/searchFriends";
    }

}
