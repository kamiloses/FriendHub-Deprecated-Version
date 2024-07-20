package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.FriendToGroupDto;
import com.application.friendhub.loggedUser.dto.FriendsListDto;
import com.application.friendhub.loggedUser.service.FriendsListService;
import com.application.friendhub.loggedUser.service.PublicChatService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.*;

@Controller

public class AllFriendsController {

    private final UserRepository userRepository;
    private final FriendsListService friendsListService;
    private final PublicChatService publicChatService;


    public AllFriendsController(UserRepository userRepository, FriendsListService friendsListService, PublicChatService publicChatService) {
        this.userRepository = userRepository;
        this.friendsListService = friendsListService;

        this.publicChatService = publicChatService;
    }

    @GetMapping("/friendHub/friends")
    public String ListOfYourFriends(Model model) {

        //todo skopiuj to do publicChatService service

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(email).orElseThrow();
        List<FriendsListEntity> allFriends = userRepository.findAllFriendListEntityById(user.getId());
        ArrayList<Long> allPossibleGroupToAdd = new ArrayList<>();
        for (PublicChatEntity publicChatEntity : user.getPublicChats()) {
            allPossibleGroupToAdd.add(publicChatEntity.getId());
        }


        List<FriendsListDto> allFriendsWithGroupToAdd = friendsListService.friendsListWithGroupEntityToDto(allFriends, allPossibleGroupToAdd);
        model.addAttribute("allFriends", allFriendsWithGroupToAdd);
        return "html/loggedUser/listOfYourFriends";
    }


    @PostMapping("/friendHub/friends/addToGroup")
    public String addFriendToGroup(@ModelAttribute FriendToGroupDto friendToGroupDto) {

        publicChatService.addFriendToGroup(friendToGroupDto);


        return "redirect:/friendHub/friends";
    }


}
