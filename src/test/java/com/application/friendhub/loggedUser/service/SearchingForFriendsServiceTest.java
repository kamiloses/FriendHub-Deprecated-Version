package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")

class SearchingForFriendsServiceTest {

    @Mock
    private SearchingForFriendsService searchingForFriendsService;

    @Mock
    private  Authentication authentication;

    @Mock
    private TestEntityManager entityManager;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {

        UserEntity myAccount = new UserEntity();
        myAccount.setEmail("kamiloses04@gmail.com");
        myAccount.setPassword("kamil123");

        UserDetailsEntity myAccountDetails = new UserDetailsEntity();
        myAccountDetails.setFirstName("Jan");
        myAccountDetails.setLastName("Kowalski");
        myAccountDetails.setUserEntity(myAccount);

        when(authentication.getName()).thenReturn(myAccount.getEmail());
        when(authentication.getPrincipal()).thenReturn(myAccount);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void findUserByNameOrSurname1() {


        UserDetailsEntity user1 = new UserDetailsEntity();
        user1.setFirstName("Adam");
        user1.setLastName("Nowak");
        UserDetailsEntity user2 = new UserDetailsEntity();
        user1.setFirstName("Adam");
        user2.setLastName("wójcik");


        List<UserDetailsEntity> mockUsers = List.of(user1,user2);

        when(searchingForFriendsService.findUserByNameOrSurname("Jan")).thenReturn(mockUsers);

        List<UserDetailsEntity> allMatchingUsers = searchingForFriendsService.findUserByNameOrSurname("jan");
        assertNotNull(allMatchingUsers);

    }

//    @Test
//    void findUserByNameOrSurname2() {
//
//
//        UserDetailsEntity user1 = new UserDetailsEntity();
//        user1.setFirstName("Jan");
//        user1.setLastName("Kowalski");
//        UserDetailsEntity user2 = new UserDetailsEntity();
//        user2.setFirstName("Jan");
//        user2.setLastName("Kowalski");
//
//
//        List<UserDetailsEntity> mockUsers = List.of(user1, user2);
//
//        when(searchingForFriendsService.findUserByNameOrSurname("Jan")).thenReturn(mockUsers);
//
//        List<UserDetailsEntity> allMatchingUsers = searchingForFriendsService.findUserByNameOrSurname("Jan");
//        assertNotNull(allMatchingUsers);
//        // Optionally verify the list contents
//        assertEquals(2, allMatchingUsers.size());
//    }

//    @Test
//    void findUserByNameOrSurname3() {
//
//
//        UserDetailsEntity user1 = new UserDetailsEntity();
//        user1.setFirstName("Jan");
//        user1.setLastName("Kowalski");
//        UserDetailsEntity user2 = new UserDetailsEntity();
//        user2.setFirstName("Jan");
//        user2.setLastName("Kowalski");
//
//
//        List<UserDetailsEntity> mockUsers = List.of(user1,user2);
//
//        when(searchingForFriendsService.findUserByNameOrSurname(userDetails1.getFirstName())).thenReturn(mockUsers);
//
//        List<UserDetailsEntity> allMatchingUsers = searchingForFriendsService.findUserByNameOrSurname(userDetails1.getFirstName());
//
//        assertNotNull(allMatchingUsers);
//    }
//    @Test
//    void findUserByNameOrSurname4() {
//
//
//        UserDetailsEntity user1 = new UserDetailsEntity();
//        user1.setFirstName("Jan");
//        user1.setLastName("Kowalski");
//        UserDetailsEntity user2 = new UserDetailsEntity();
//        user2.setFirstName("Jan");
//        user2.setLastName("Kowalski");
//
//
//        List<UserDetailsEntity> mockUsers = List.of(user1,user2);
//
//        when(searchingForFriendsService.findUserByNameOrSurname(userDetails1.getFirstName())).thenReturn(mockUsers);
//
//        List<UserDetailsEntity> allMatchingUsers = searchingForFriendsService.findUserByNameOrSurname(userDetails1.getFirstName());
//
//        assertNotNull(allMatchingUsers);
//    }
}