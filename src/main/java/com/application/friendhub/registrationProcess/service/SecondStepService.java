package com.application.friendhub.registrationProcess.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecondStepService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public SecondStepService(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    public void createUserAccount(UserEntity userEntity, UserDetailsEntity userDetailsEntity){

        userRepository.save(userEntity);
        userDetailsRepository.save(userDetailsEntity);
        log.info("User successfully registered");

    }


}
