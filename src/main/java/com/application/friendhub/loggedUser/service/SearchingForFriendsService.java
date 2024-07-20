package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SearchingForFriendsService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public SearchingForFriendsService(UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }


    public List<UserDetailsEntity> findUserByNameOrSurname(@RequestParam(required = false) String fullName) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow();


        List<UserDetailsEntity> foundUsers;
        String firstName;
        String lastName;
        String[] parts = fullName.split(" ");
        if (parts.length == 2) {
            firstName = parts[0];
            lastName = parts[1];
            foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName, user.getId());
            if (foundUsers.isEmpty()) {
                firstName = parts[1];
                lastName = parts[0];
                foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName, user.getId());
            }
        } else {
            foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(fullName, fullName, user.getId());


        }

        return foundUsers;
    }
}