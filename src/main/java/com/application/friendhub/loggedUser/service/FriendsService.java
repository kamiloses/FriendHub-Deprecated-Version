package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class FriendsService {

    UserDetailsRepository userDetailsRepository;

    public FriendsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    public void findUserByNameOrSurname(Model model, @RequestParam(required = false) String fullName) {
        String firstName;
        String lastName;
        String[] parts = fullName.split(" ");
        if (parts.length == 2) {
            firstName = parts[0];
            lastName = parts[1];
            List<UserDetailsEntity> foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName);
            model.addAttribute("foundUsers", foundUsers);
            if (foundUsers.isEmpty()) {
                firstName = parts[1];
                lastName = parts[0];
                List<UserDetailsEntity> replacedNameAndLastNameUser = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName);
                model.addAttribute("foundUsers", replacedNameAndLastNameUser);
            }
        } else {
            List<UserDetailsEntity> foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(fullName, fullName);
            model.addAttribute("foundUsers", foundUsers);

        }


    }
}


