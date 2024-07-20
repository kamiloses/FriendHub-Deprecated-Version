package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.*;
import com.application.friendhub.loggedUser.dto.LikeDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/friendHub/profile/userPage")
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




        List<LikesEntity> likesEntity = likeRepository.findLikesByTimelineEntities(posts);
      /*  ArrayList<Long> objects = new ArrayList<>();
        for (LikesEntity entity : likesEntity) {
            objects.add(entity.getUserEntity().getId());
        }
        List<LikesEntity> likesEntities = likeRepository.findByLikeEntityIdIn(objects);

        System.out.println(likesEntities.size());


        System.out.println(likesEntities);*/

        Map<Long, Integer> postLikesCountMap = new HashMap<>();
        for (TimelineEntity post : posts) {
            int likesCount = 0;
            for (LikesEntity like : likesEntity) {
                if (like.getTimelineEntity().getId().equals(post.getId())) {
                    likesCount++;
                }
            }
            postLikesCountMap.put(post.getId(), likesCount);
        }

        System.out.println(postLikesCountMap.size());
        model.addAttribute("postLikesCountMap", postLikesCountMap);










        HashMap<Long, Boolean> isLiked = likeService.isCommentLiked(posts, likesEntity);

        model.addAttribute("isLiked",isLiked);



        return "html/loggedUser/friend-profile";
    }


    @PostMapping("/friendHub/home/addLikeToSomeone")
    public String addLike(@ModelAttribute LikeDto likeDto) {
        TimelineEntity timelineEntity = timelineRepository.findById(likeDto.getLikesId()).orElseThrow(() -> new UsernameNotFoundException("not found"));

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("not found"));

      /*  if (likeRepository.existsByLikeEntityId(likeDto.getLikesId()) && userRepository.existsById(user.getId()) && likeRepository.existsByUserEntityId(user.getId())) {
            LikesEntity likesEntity = likeRepository.findByLikeEntity_IdAndUserEntity_Id(likeDto.getLikesId(), user.getId());
            likeRepository.delete(likesEntity);*/

        if (likeRepository.existsByTimelineEntityIdAndUserEntityId(likeDto.getLikesId(),user.getId())&& userRepository.existsById(user.getId())){
            LikesEntity likesEntity = likeRepository.findByTimelineEntity_IdAndUserEntity_Id(likeDto.getLikesId(), user.getId());
            likeRepository.delete(likesEntity);


        } else {
            LikesEntity likesEntity = likeService.likeDtoToEntity(likeDto);
            likeRepository.save(likesEntity);
        }


        return "redirect:friendHub/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();
    }


    @PostMapping("/friendHub/home/addCommentOnFriendPaige")//todo zamien urk
    public String addComment(@ModelAttribute CommentDto commentDto) {

        CommentsEntity commentsEntity = commentService.commentDtoToEntity(commentDto);
        commentsRepository.save(commentsEntity);
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new UsernameNotFoundException("s"));

        return "redirect:/friendHub/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();

    }


    @PostMapping("/friendHub/home/addReplyOnFriendPaige")
    public String addReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {
        CommentsEntity parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        CommentsEntity reply = commentService.commentDtoToEntity(commentDto);
        reply.setParentComment(parentComment);
        reply.setTimelineEntity(parentComment.getTimelineEntity());
        commentsRepository.save(reply);
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new RuntimeException("timeline not found"));
        return "redirect:/friendHub/profile" + "?firstName=" + timelineEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + timelineEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + timelineEntity.getUser().getId();
    }





}


