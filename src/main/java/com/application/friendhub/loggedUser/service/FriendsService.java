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


    public List<UserDetailsEntity> findUserByNameOrSurname( @RequestParam(required = false) String fullName) {
        List<UserDetailsEntity> foundUsers=null;
        String firstName;
        String lastName;
        String[] parts = fullName.split(" ");
        if (parts.length == 2) {
            firstName = parts[0];
            lastName = parts[1];
             foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName);
            if (foundUsers.isEmpty()) {
                firstName = parts[1];
                lastName = parts[0];
                foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(firstName, lastName);
            }
        } else {
             foundUsers = userDetailsRepository.findUserDetailsEntitiesByFirstNameOrLastName(fullName, fullName);


        }

return foundUsers;}
}