package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.FriendsListRepository;
import com.application.friendhub.Repository.PrivateChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddFriendsService {

    private final UserRepository userRepository;
    private final FriendsListRepository friendsListRepository;
    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatService privateChatService;

    public AddFriendsService(UserRepository userRepository, FriendsListRepository friendsListRepository, PrivateChatRepository privateChatRepository, PrivateChatService privateChatService) {
        this.userRepository = userRepository;
        this.friendsListRepository = friendsListRepository;
        this.privateChatRepository = privateChatRepository;
        this.privateChatService = privateChatService;
    }


    public FriendsListEntity invitedFriendsDtoToEntity(FriendsDto invitedFriend) {
        UserEntity invitedFriendEntity = userRepository.findUserEntityById(invitedFriend.getId());

        String myEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity myAccount = userRepository.findUserEntityByEmail(myEmail).get();


        FriendsListEntity friendsListEntity = new FriendsListEntity();
        friendsListEntity.setFirstName(invitedFriend.getFirstName());
        friendsListEntity.setLastName(invitedFriend.getLastName());
        friendsListEntity.setFriendshipDate(new Date());
        friendsListEntity.setUserEntity(myAccount);
        friendsListEntity.setConnectionToYourOwnAccount(invitedFriendEntity);


        return friendsListEntity;
    }






    public FriendsListEntity invitingFriendsDtoToEntity(FriendsDto invitedFriend) {
        String myEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity myAccount = userRepository.findUserEntityByEmail(myEmail).get();

        UserEntity addedFriend = userRepository.findUserEntityById(invitedFriend.getId());

        FriendsListEntity friendsListEntity = new FriendsListEntity();
        friendsListEntity.setFirstName(myAccount.getUserDetailsEntity().getFirstName());
        friendsListEntity.setLastName(myAccount.getUserDetailsEntity().getLastName());
        friendsListEntity.setFriendshipDate(new Date());
        friendsListEntity.setUserEntity(addedFriend);
        friendsListEntity.setConnectionToYourOwnAccount(myAccount);


        return friendsListEntity;
    }


    public void removeFriend(FriendsDto friendsDto){
        FriendsListEntity invitedAccount = invitedFriendsDtoToEntity(friendsDto);
        FriendsListEntity invitingAccount =invitingFriendsDtoToEntity(friendsDto);

        FriendsListEntity you = friendsListRepository.findByConnectionToYourOwnAccount_IdAndUserEntity_Id(invitingAccount.getConnectionToYourOwnAccount().getId(), invitingAccount.getUserEntity().getId());
        FriendsListEntity he = friendsListRepository.findByConnectionToYourOwnAccount_IdAndUserEntity_Id(invitedAccount.getConnectionToYourOwnAccount().getId(), invitedAccount.getUserEntity().getId());

        PrivateChatEntity privateChatEntitiesByUser1IdAndUser2Id = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(invitingAccount.getConnectionToYourOwnAccount().getId(), invitingAccount.getUserEntity().getId());

        privateChatRepository.delete(privateChatEntitiesByUser1IdAndUser2Id);
        friendsListRepository.delete(he);
        friendsListRepository.delete(you);

    }
public void addFriend(FriendsDto friendsDto){

    FriendsListEntity invitedAccount = invitedFriendsDtoToEntity(friendsDto);
    FriendsListEntity invitingAccount = invitingFriendsDtoToEntity(friendsDto);


    friendsListRepository.save(invitedAccount);
    friendsListRepository.save(invitingAccount);
    privateChatRepository.save(privateChatService.createPrivateChatService(invitedAccount, invitingAccount));


}

}
