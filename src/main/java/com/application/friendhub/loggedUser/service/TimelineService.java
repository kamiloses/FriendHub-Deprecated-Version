package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.TimelineDto;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimelineService {


    private final TimelineRepository timelineRepository;
    private UserRepository userRepository;

    private UserDetailsRepository userDetailsRepository;

    public TimelineService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, TimelineRepository timelineRepository, TimelineRepository timelineRepository1) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.timelineRepository = timelineRepository1;
    }


    public TimelineDto timelineEntityToDto(TimelineEntity timelineEntity) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("a"));
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setId(timelineEntity.getId());
        timelineDto.setAuthor(timelineEntity.getAuthor());
        timelineDto.setUploadedUser(timelineEntity.getUploadedUser());
        timelineDto.setPost(timelineEntity.getPost());
        timelineDto.setImage(timelineEntity.getImage());
        timelineDto.setDateOfPublication(new Date());
//        timelineDto.setUser(timelineEntity.getId());
        return timelineDto;
    }

    public TimelineEntity timelineDtoToEntity(TimelineDto timelineDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("as"));
        TimelineEntity post = timelineRepository.findById(timelineDto.getId()).orElseThrow(()->new UsernameNotFoundException("not found"));

        TimelineEntity timelineEntity = new TimelineEntity();
         timelineEntity.setUser(user);
         timelineEntity.setUploadedUser(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
          timelineEntity.setAuthor(post.getAuthor());
        timelineEntity.setImage("example.jpg");
        timelineEntity.setDateOfPublication(new Date());


    return timelineEntity;
    }

    public TimelineEntity  addPostEntityToDto(TimelineDto timelineDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("as"));


        TimelineEntity timelineEntity = new TimelineEntity();
        timelineEntity.setUploadedUser(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
        timelineEntity.setAuthor(user.getUserDetailsEntity().getFirstName() +" "+user.getUserDetailsEntity().getLastName());
        timelineEntity.setImage("example.jpg");
        timelineEntity.setDateOfPublication(new Date());
       timelineEntity.setPost(timelineDto.getPost());
        timelineEntity.setUser(user);


        return timelineEntity;










    }



    }
