package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.*;
import com.application.friendhub.loggedUser.dto.LikeDto;
import com.application.friendhub.loggedUser.dto.TimelineDto;
import com.application.friendhub.loggedUser.dto.*;
import com.application.friendhub.loggedUser.service.*;
import com.application.friendhub.loggedUser.service.PublicChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@Controller
@Slf4j
public class HomeController {
    private PublicChatService publicChatService;
    private final EncodedImageService encodedImageService;
    private final TimelineService timelineService;
    private final TimelineRepository timelineRepository;
    private FriendsListService friendsListService;
    private final UserDetailsRepository userDetailsRepository;


    private final UserRepository userRepository;

    private final AddFriendsService addFriendsService;
    private final MessagesService messagesService;

    private final CommentService commentService;
    private final LikeService likeService;
    private LikeRepository likeRepository;
    private MessageRepository messageRepository;
    private PublicChatRepository publicChatRepository;


    public HomeController(PublicChatService publicChatService, EncodedImageService encodedImageService, TimelineService timelineService, TimelineRepository timelineRepository, FriendsListService friendsListService, UserDetailsRepository userDetailsRepository, UserRepository userRepository, AddFriendsService addFriendsService, MessagesService messagesService, CommentService commentService, LikeService likeService, LikeRepository likeRepository, MessageRepository messageRepository, PublicChatRepository publicChatRepository) {
        this.publicChatService = publicChatService;
        this.encodedImageService = encodedImageService;
        this.timelineService = timelineService;
        this.friendsListService = friendsListService;
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;

        this.timelineRepository = timelineRepository;
        this.addFriendsService = addFriendsService;
        this.messagesService = messagesService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.likeRepository = likeRepository;
        this.messageRepository = messageRepository;
        this.publicChatRepository = publicChatRepository;
    }

//todo zoptymalizuj kod
    @GetMapping("/friendHub/home")
    public String home(Model model) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).get();
        List<TimelineEntity> allPosts = userRepository.findAllTimelineEntityByEmail(email);
        UserDetailsEntity userDetails = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);
        List<FriendsListEntity> user = userRepository.findAllFriendListEntityById(userEntity.getId());
        String firstNameAndLastName = userDetails.getFirstName() + " " + userDetails.getLastName();

        String encodedProfilePicture = encodedImageService.encodedImage(userDetails.getProfilePicture());
        model.addAttribute("profilePicture", encodedProfilePicture);


        model.addAttribute("nameAndSurname", firstNameAndLastName);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("userDetails", userDetails);

        List<FriendsListDto> userDto = friendsListService.friendsListEntityToDto(user);
        model.addAttribute("allFriends", userDto);


        List<LikesEntity> likesEntities = likeRepository.findLikesByTimelineEntities(allPosts);

        Map<Long, Integer> postLikesCountMap = likeService.allPostLikesCountMap(allPosts, likesEntities);
        model.addAttribute("postLikesCountMap", postLikesCountMap);


        HashMap<Long, Boolean> isLiked = likeService.isCommentLiked(allPosts, likesEntities);
        model.addAttribute("isLiked", isLiked);


        List<PrivateChatEntity> privateChatByEmail = userRepository.findAllPrivateChatByEmail(userEntity.getEmail());
        List<Long> privateChatId = privateChatByEmail.stream().map(PrivateChatEntity::getId).toList();
        System.out.println(privateChatId);
        List<MessagesEntity> allMessages = messageRepository.findAllMessagesRelatedWithPrivateChat(privateChatId);


        List<MessagesDTO> messagesDTOS = messagesService.messagesEntityToDto(allMessages);


        model.addAttribute("messagesRelatedWithYou", messagesDTOS);
        List<PrivateChatEntity> list = privateChatByEmail.stream().filter(chatMessage -> chatMessage.getMessages().stream().anyMatch(messageId -> messageId.getPrivateChats_id().getId().equals(chatMessage.getId()))).toList();


        //List<PrivateChatDto> previousMessagesDto = privateChatService.privateChatEntityToDto(list);
        // model.addAttribute("previousMessages",previousMessagesDto);

        List<PublicChatEntity> allYourGroups = publicChatRepository.findAllByUserEntity(userEntity);
        List<PublicChatDto> publicChatDto = publicChatService.mapEntitiesToDto(allYourGroups);
        model.addAttribute("publicChat", publicChatDto);


        return "html/loggedUser/mainPaige";
        //friends possible to add to your group

    }

    @PostMapping("/friendHub/home/add")
    public String addPostOnHomeSide(@ModelAttribute TimelineDto timelineDto) throws IOException {
        TimelineEntity timelineEntity = timelineService.addPostEntityToDto(timelineDto);

        timelineRepository.save(timelineEntity);
        log.error("saved");


        return "redirect:/friendHub/home";
    }

    @PostMapping("/friendHub/searchFriends/remove")
    public String removeFriend(@ModelAttribute FriendsDto friendsDto) {
        addFriendsService.removeFriend(friendsDto);


        return "redirect:/friendHub/searchFriends";
    }


    @PostMapping("/friendHub/home/removePost")//todo zmien na delete mapping
    public String removePost(@ModelAttribute TimelineDto timelineDto) {
        timelineRepository.deleteById(timelineDto.getId());


        return "redirect:/friendHub/home";
    }


    @PostMapping("/friendHub/upload")
    public String upload(@ModelAttribute TimelineDto timelineDto) throws IOException {
        TimelineEntity savedEntity = timelineRepository.findById(timelineDto.getId()).orElseThrow(() -> new UsernameNotFoundException("not found"));
        timelineRepository.save(timelineService.timelineDtoToEntity(timelineDto));


        return "redirect:/friendHub/profile" + "?firstName=" + savedEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + savedEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + savedEntity.getUser().getId();
    }


    @PostMapping("/friendHub/home/addComment")//todo zamien urk
    public String addComment(@ModelAttribute CommentDto commentDto) {

        commentService.addComment(commentDto);


        return "redirect:/friendHub/home";
    }

    @PostMapping("/friendHub/home/addLike")
    public String addLike(@ModelAttribute LikeDto likeDto) {

        likeService.addLike(likeDto);

        return "redirect:/friendHub/home";
    }


    @PostMapping("/friendHub/home/addReply")
    public String addReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {

        commentService.addReply(commentDto, parentCommentId);

        return "redirect:/friendHub/home";
    }

    /*  @PostMapping("/home/removeReply")
      public String removeReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {


      }
  */
    @PostMapping("/friendHub/home/createPublicChat")
    public String createPublicChat(@ModelAttribute PublicChatDto publicChatDto) throws IOException {
        publicChatService.createPublicChat(publicChatDto);


        return "redirect:/friendHub/home";
    }


}




