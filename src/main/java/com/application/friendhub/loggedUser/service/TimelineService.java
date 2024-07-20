package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.TimelineDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class TimelineService {


    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;

    private final UserDetailsRepository userDetailsRepository;

    public TimelineService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, TimelineRepository timelineRepository, TimelineRepository timelineRepository1) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.timelineRepository = timelineRepository1;
    }


    public TimelineDto timelineEntityToDto(TimelineEntity timelineEntity) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).get();
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setId(timelineEntity.getId());
        timelineDto.setAuthor(timelineEntity.getAuthor());
        timelineDto.setUploadedUser(timelineEntity.getUploadedUser());
        timelineDto.setPost(timelineEntity.getPost());
        timelineDto.setDateOfPublication(new Date());
//        timelineDto.setUser(timelineEntity.getId());
        return timelineDto;
    }

    public TimelineEntity timelineDtoToEntity(TimelineDto timelineDto) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).get();
        TimelineEntity post = timelineRepository.findById(timelineDto.getId()).get();

        TimelineEntity timelineEntity = new TimelineEntity();
         timelineEntity.setUser(user);
         timelineEntity.setUploadedUser(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
          timelineEntity.setAuthor(post.getAuthor());
          timelineEntity.setTimelineImage(timelineDto.getMultipartFile().getBytes());
        timelineEntity.setDateOfPublication(new Date());


    return timelineEntity;
    }

    public TimelineEntity  addPostEntityToDto(TimelineDto timelineDto) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).get();


        TimelineEntity timelineEntity = new TimelineEntity();
        timelineEntity.setUploadedUser(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
        timelineEntity.setAuthor(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
        timelineEntity.setDateOfPublication(new Date());
       timelineEntity.setPost(timelineDto.getPost());
       timelineEntity.setTimelineImage(timelineDto.getMultipartFile().getBytes());
        timelineEntity.setUser(user);


        return timelineEntity;










    }



    }
