package com.application.friendhub.loggedUser.controller;

import com.application.friendhub.Entity.*;
import com.application.friendhub.Repository.*;
import com.application.friendhub.dto.LikeDto;
import com.application.friendhub.dto.TimelineDto;
import com.application.friendhub.loggedUser.dto.CommentDto;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import com.application.friendhub.loggedUser.dto.ProfileDto;
import com.application.friendhub.loggedUser.service.*;
import com.application.friendhub.websocket.chat.ChatController;
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

    private final CommentsRepository commentsRepository;
    private final TimelineService timelineService;
    private final TimelineRepository timelineRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final ProfileDtoService profileDtoService;

    private final UserRepository userRepository;
    private final FriendsService friendsService;

    private final AddFriendsService addFriendsService;
    private FriendsListRepository friendsListRepository;

    private CommentService commentService;
    private LikeService likeService;
    private LikeRepository likeRepository;
    private PrivateChatService privateChatService;
    private ChatController controller;
    private PrivateChatRepository privateChatRepository;
    private MessageRepository messageRepository;


    public HomeController(CommentsRepository commentsRepository, TimelineService timelineService, TimelineRepository timelineRepository, UserDetailsRepository userDetailsRepository, ProfileDtoService profileDtoService, UserRepository userRepository, FriendsService friendsService, AddFriendsService addFriendsService, FriendsListRepository friendsListRepository, CommentService commentService, LikeService likeService, LikeRepository likeRepository, ChatController controller, PrivateChatRepository privateChatRepository, MessageRepository messageRepository) {
        this.commentsRepository = commentsRepository;
        this.timelineService = timelineService;
        this.userDetailsRepository = userDetailsRepository;
        this.profileDtoService = profileDtoService;
        this.userRepository = userRepository;
        this.friendsService = friendsService;
        this.timelineRepository = timelineRepository;
        this.addFriendsService = addFriendsService;
        this.friendsListRepository = friendsListRepository;
        this.commentService = commentService;
        this.likeService = likeService;
        this.likeRepository = likeRepository;
        this.controller = controller;
        this.privateChatRepository = privateChatRepository;
        this.messageRepository = messageRepository;
    }


    @GetMapping("/home")
    public String home(Model model) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));
        List<TimelineEntity> allPosts = userRepository.findAllTimelineEntityByEmail(email);
        UserDetailsEntity userDetails = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);
        List<FriendsListEntity> user = userRepository.findAllFriendListEntityById(userEntity.getId());
        /*List<CommentsEntity> commentsE = timelineRepository.findCommentsE(allPosts);*/
        String firstNameAndLastName = userDetails.getFirstName() + " " + userDetails.getLastName();


        if (userDetails.getProfilePicture() != null) {  //todo chwilowo
            String base64Image = Base64.getEncoder().encodeToString(userDetails.getProfilePicture());
            model.addAttribute("profilePicture", base64Image);
        }


        model.addAttribute("nameAndSurname", firstNameAndLastName);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("allFriends", user);


        //        model.addAttribute("image", Base64.getEncoder().encodeToString(userDetails.getProfilePicture()));
            /*    List<Long> timelineIds = allPosts.stream()
                        .map(TimelineEntity::getId)
                        .collect(Collectors.toList());*/

        List<LikesEntity> likesEntities = likeRepository.findLikesByTimelineEntities(allPosts);


        Map<Long, Integer> postLikesCountMap = new HashMap<>();
        for (TimelineEntity post : allPosts) {
            int likesCount = 0;
            for (LikesEntity like : likesEntities) {
                if (like.getLikeEntity().getId().equals(post.getId())) {
                    likesCount++;
                }
            }
            postLikesCountMap.put(post.getId(), likesCount);
        }
        log.error(postLikesCountMap.toString());

        System.out.println(postLikesCountMap.size());
        model.addAttribute("postLikesCountMap", postLikesCountMap);


        HashMap<Long, Boolean> isLiked = likeService.isCommentLiked(allPosts, likesEntities);

        model.addAttribute("isLiked", isLiked);


                /*String image ="data:image/jpg;base64," + Base64.getEncoder().encodeToString(userDetails.getProfilePicture());
                     model.addAttribute("image", image);
                String base64Image = Base64.getEncoder().encodeToString(userDetails.getProfilePicture());
                byte[] imageData = userDetails.getProfilePicture();
                try (FileOutputStream fos = new FileOutputStream("C:\\Riot Games\\obrazek.jpg")) {
                    fos.write(base64Image.getBytes());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/


        List<PrivateChatEntity> privateChatByEmail = userRepository.findAllPrivateChatByEmail(userEntity.getEmail());
        List<Long> privateChatId = privateChatByEmail.stream().map(PrivateChatEntity::getId).toList();
        System.out.println(privateChatId);
        List<MessagesEntity> allMessages = messageRepository.findAllMessagesRelatedWithPrivateChat(privateChatId);
        model.addAttribute("allMessages", allMessages);


        List<MessagesEntity> messagesEntityBySenderIdOrSenderId = messageRepository.findMessagesEntityBySenderIdOrReceiverId(userEntity.getId(), userEntity.getId());

        System.out.println("abcd"+messagesEntityBySenderIdOrSenderId.size());
        model.addAttribute("messagesRelatedWithYou",messagesEntityBySenderIdOrSenderId);

        List<PrivateChatEntity> list =privateChatByEmail.stream().filter(chatMessage->chatMessage.getMessages().stream().anyMatch(messageId->messageId.getPrivateChats_id().getId().equals(chatMessage.getId()))).toList();


        model.addAttribute("previousMessages",list);






//jezeli privatechat jest równy mojemu id oraz first
        return "html/mainPaige";
    }


    @PostMapping("/home/add")
    public String addPostOnHomeSide(@ModelAttribute TimelineDto timelineDto) {
        TimelineEntity timelineEntity = timelineService.addPostEntityToDto(timelineDto);

        timelineRepository.save(timelineEntity);
        log.error("saved");


        return "redirect:/home";
    }


    @GetMapping("/friendhub/friends")
    public String friends(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow();
        List<FriendsListEntity> allFriends = userRepository.findAllFriendListEntityById(user.getId());

        model.addAttribute("allFriends", allFriends);


        return "html/friendsPaige";
    }

    @GetMapping("/friendhub/profile")
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(email);

        model.addAttribute("userDetailsEntity", userDetailsEntity);
    /*UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);
    userDetailsRepository.save(userDetailsEntity);*/


        return "html/ProfilePage";
    }

  /*  @PostMapping("friendHub/home/delete")
    public String home(UserDto){
        u


    return "redirect:/home";}
*/


    @PostMapping("/friendhub/profile/modifyProfile")
    public String modifyProfile(ProfileDto profileDto) throws IOException {
        UserDetailsEntity userDetailsEntity = profileDtoService.profileDtoToUserDetailsEntity(profileDto);


        userDetailsRepository.save(userDetailsEntity);


        return "redirect:/friendhub/profile";
    }


    @GetMapping("/friendhub/searchFriends")
    public String searchFriends(Model model, String fullName) {
        List<UserDetailsEntity> user = friendsService.findUserByNameOrSurname(fullName);

//        String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
        model.addAttribute("foundUsers", user);



        List<Long> isHeYourFriend=new ArrayList<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity loggedUser = userRepository.findUserEntityByEmail(name).orElseThrow();

        for (UserDetailsEntity userDetailsEntity : user) {
        for (FriendsListEntity friendsListEntity : loggedUser.getFriendsListEntities()) {
                if (friendsListEntity.getConnectionToYourOwnAccount().getId().equals(userDetailsEntity.getId())) {
                    isHeYourFriend.add(userDetailsEntity.getId());

                }
            }
model.addAttribute("isHeYourFriend", isHeYourFriend);

        }


        return "html/searchFriends";


    }

    @PostMapping("/friendhub/searchFriends/searchAll")
    public String searchFriend(ProfileDto profileDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();


        return "redirect:/friendhub/searchFriends";
    }


    @PostMapping("/friendhub/searchFriends/add")
    public String addFriend(@ModelAttribute FriendsDto friendsDto) {

        FriendsListEntity invitedAccount = addFriendsService.invitedFriendsDtoToEntity(friendsDto);
        FriendsListEntity invitingAccount = addFriendsService.invitingFriendsDtoToEntity(friendsDto);


        friendsListRepository.save(invitedAccount);
        friendsListRepository.save(invitingAccount);
        privateChatRepository.save(addFriendsService.privateChatService(invitedAccount, invitingAccount));


        return "redirect:/friendhub/searchFriends";
    }

    @PostMapping("/friendhub/searchFriends/remove")
    public String removeFriend(@ModelAttribute FriendsDto friendsDto) {
        System.out.println(friendsDto);
        FriendsListEntity invitedAccount = addFriendsService.invitedFriendsDtoToEntity(friendsDto);
        FriendsListEntity invitingAccount = addFriendsService.invitingFriendsDtoToEntity(friendsDto);
        FriendsListEntity you = friendsListRepository.findByConnectionToYourOwnAccount_IdAndUserId_Id(invitingAccount.getConnectionToYourOwnAccount().getId(), invitingAccount.getUserId().getId());
        FriendsListEntity he = friendsListRepository.findByConnectionToYourOwnAccount_IdAndUserId_Id(invitedAccount.getConnectionToYourOwnAccount().getId(), invitedAccount.getUserId().getId());
        PrivateChatEntity privateChatEntitiesByUser1IdAndUser2Id = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(invitingAccount.getConnectionToYourOwnAccount().getId(), invitingAccount.getUserId().getId());

        privateChatRepository.delete(privateChatEntitiesByUser1IdAndUser2Id);
        friendsListRepository.delete(he);
        friendsListRepository.delete(you);


        return "redirect:/friendhub/searchFriends";
    }








    @PostMapping("/home/removePost")//todo zmien na delete mapping
    public String removePost(@ModelAttribute TimelineDto timelineDto) {
        timelineRepository.deleteById(timelineDto.getId());


        return "redirect:/home";
    }


    @PostMapping("/friendhub/upload")
    public String upload(@ModelAttribute TimelineDto timelineDto) {
        TimelineEntity savedEntity = timelineRepository.findById(timelineDto.getId()).orElseThrow(() -> new UsernameNotFoundException("not found"));
        timelineRepository.save(timelineService.timelineDtoToEntity(timelineDto));


        return "redirect:/profile" + "?firstName=" + savedEntity.getUser().getUserDetailsEntity().getFirstName() +
                "&lastName=" + savedEntity.getUser().getUserDetailsEntity().getLastName() +
                "&id=" + savedEntity.getUser().getId();
    }


    @PostMapping("/home/addComment")//todo zamien urk
    public String addComment(@ModelAttribute CommentDto commentDto) {

        CommentsEntity commentsEntity = commentService.commentDtoToEntity(commentDto);
        commentsRepository.save(commentsEntity);


        return "redirect:/home";
    }

    @PostMapping("/home/addLike")
    public String addLike(@ModelAttribute LikeDto likeDto) {
        TimelineEntity timelineEntity = timelineRepository.findById(likeDto.getLikesId()).orElseThrow(() -> new UsernameNotFoundException("not found"));

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("not found"));

//        if (likeRepository.existsByLikeEntityId(likeDto.getLikesId()) && userRepository.existsById(user.getId()) && likeRepository.existsByUserEntityId(user.getId())) { //todo sprawdz czy nie usunąc likeRepository.existsByLikeEntityId(likeDto.getLikesId()
        if (likeRepository.existsByLikeEntityIdAndUserEntityId(likeDto.getLikesId(), user.getId()) && userRepository.existsById(user.getId())) {
            LikesEntity likesEntity = likeRepository.findByLikeEntity_IdAndUserEntity_Id(likeDto.getLikesId(), user.getId());
            likeRepository.delete(likesEntity);

        } else {
            LikesEntity likesEntity = likeService.likeDtoToEntity(likeDto);
            likeRepository.save(likesEntity);
        }


        return "redirect:/home";
    }


    @PostMapping("/home/addReply")
    public String addReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {
        CommentsEntity parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        CommentsEntity reply = commentService.commentDtoToEntity(commentDto);
        reply.setParentComment(parentComment);
        reply.setTimelineEntity(parentComment.getTimelineEntity());
        commentsRepository.save(reply);

        return "redirect:/home";
    }

  /*  @PostMapping("/home/removeReply")
    public String removeReply(@ModelAttribute CommentDto commentDto, @RequestParam Long parentCommentId) {




    }
*/
}




