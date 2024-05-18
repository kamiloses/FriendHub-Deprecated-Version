package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.*;
import com.application.friendhub.dto.LikeDto;
import com.application.friendhub.loggedUser.dto.CommentDto;
import com.application.friendhub.loggedUser.service.CommentService;
import com.application.friendhub.loggedUser.service.LikeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class userPageController {
    private final TimelineRepository timelineRepository;

    private final UserRepository userRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final LikeRepository likeRepository;
    private final LikeService likeService;
    private final CommentService commentService;
    private final CommentsRepository commentsRepository;

    public userPageController(TimelineRepository timelineRepository, UserRepository userRepository, UserDetailsRepository userDetailsRepository, LikeRepository likeRepository, LikeService likeService, CommentService commentService, CommentsRepository commentsRepository) {
        this.timelineRepository = timelineRepository;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.likeRepository = likeRepository;
        this.likeService = likeService;
        this.commentService = commentService;
        this.commentsRepository = commentsRepository;
    }


    @GetMapping("/profile")
    public String searchedProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam Long id, Model model) {

        List<TimelineEntity> posts = timelineRepository.findTimelineEntityByUser_Id(id);
        UserEntity searchedUser = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("not found"));


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));
        List<FriendsListEntity> user = userRepository.findAllFriendListEntityById(userEntity.getId());
        UserDetailsEntity userDetails = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);

        String firstNameAndLastName = userDetails.getFirstName() + " " + userDetails.getLastName();
        model.addAttribute("allFriends", user);
        model.addAttribute("nameAndSurname", firstNameAndLastName);

        List<TimelineEntity> postss = timelineRepository.findTimelineEntitiesWithMatchingAuthor(firstName, lastName);
        model.addAttribute("allPosts", posts);
        model.addAttribute("userDetails", searchedUser.getUserDetailsEntity());

        return "html/somebodysProfile";
    }


    @PostMapping("/home/addLikeToSomeone")
    public String addLike(@ModelAttribute LikeDto likeDto) {
        TimelineEntity timelineEntity = timelineRepository.findById(likeDto.getLikesId()).orElseThrow(() -> new UsernameNotFoundException("not found"));

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("not found"));

        if (likeRepository.existsByLikeEntityId(likeDto.getLikesId()) && userRepository.existsById(user.getId()) && likeRepository.existsByUserEntityId(user.getId())) {
            LikesEntity likesEntity = likeRepository.findByLikeEntity_IdAndUserEntity_Id(likeDto.getLikesId(), user.getId());
            likeRepository.delete(likesEntity);

        } else {
            LikesEntity likesEntity = likeService.likeDtoToEntity(likeDto);
            likeRepository.save(likesEntity);
        }


        return "redirect:/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();
    }


    @PostMapping("/home/addCommentOnSomebodyPage")//todo zamien urk
    public String addComment(@ModelAttribute CommentDto commentDto) {

        CommentsEntity commentsEntity = commentService.commentDtoToEntity(commentDto);
        commentsRepository.save(commentsEntity);
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new UsernameNotFoundException("s"));

        return "redirect:/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();

    }


    @PostMapping("/home/addReplyOnSomebodyProfile")
    public String addReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {
        CommentsEntity parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        CommentsEntity reply = commentService.commentDtoToEntity(commentDto);
        reply.setParentComment(parentComment);
        reply.setTimelineEntity(parentComment.getTimelineEntity());
        commentsRepository.save(reply);
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new RuntimeException("timeline not found"));
        return "redirect:/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();
    }





}


