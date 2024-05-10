package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.TimelineDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimelineService {


    private UserRepository userRepository;

    private UserDetailsRepository userDetailsRepository;

    public TimelineService(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    public TimelineDto timelineEntityToDto(TimelineEntity timelineEntity) {
        TimelineDto timelineDto = new TimelineDto();
        /*timelineDto.setAuthor(timelineEntity.getAuthor());*/
        timelineDto.setPost(timelineEntity.getPost());
        timelineDto.setImage(timelineEntity.getImage());
        timelineDto.setDateOfPublication(new Date());
//        timelineDto.setUser(timelineEntity.getId());
        return timelineDto;
    }

    public TimelineEntity timelineDtoToEntity(TimelineDto timelineDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);
        TimelineEntity timelineEntity = new TimelineEntity();
        timelineEntity.setAuthor(userDetailsEntity.getFirstName() + " " + userDetailsEntity.getLastName());
        timelineEntity.setPost(timelineDto.getPost());
        timelineEntity.setImage("example.jpg");
        timelineEntity.setDateOfPublication(new Date());
        timelineEntity.setUser(userEntity);

    return timelineEntity;
    }

    }
