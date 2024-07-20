package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.PublicChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.FriendToGroupDto;
import com.application.friendhub.loggedUser.dto.PublicChatDto;
import com.application.friendhub.websocket.event.FriendJoinedTheGroupEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class PublicChatService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final EncodedImageService encodedImageService;
    private final PublicChatRepository publicChatRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PublicChatService(UserService userService, UserRepository userRepository, EncodedImageService encodedImageService, PublicChatRepository publicChatRepository, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.encodedImageService = encodedImageService;
        this.publicChatRepository = publicChatRepository;
        this.eventPublisher = eventPublisher;
    }

    public PublicChatEntity publicChatDtoToEntity(PublicChatDto publicChatDto) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(name).orElseThrow();
        PublicChatEntity publicChatEntity = new PublicChatEntity();
        publicChatEntity.setName(publicChatEntity.getName());
        publicChatEntity.setName(publicChatDto.getGroupName());
        if (publicChatDto.getUserDto()==null){
            Path path = Paths.get("C:\\Users\\kamil\\IdeaProjects\\FriendHub\\src\\main\\resources\\static\\img\\defaultGroupImage.png");
            byte[] defaultImage = Files.readAllBytes(path);
            publicChatEntity.setImage(defaultImage);

        }else {
        publicChatEntity.setImage(publicChatDto.getGroupImage().getBytes());}
        publicChatEntity.setCreatedAt(new Date());
        publicChatEntity.setUserEntity(List.of(userEntity));
return publicChatEntity;
    }


    public List<PublicChatDto> mapEntitiesToDto(List<PublicChatEntity> entities) {
        return entities.stream()
                .map(entity -> {
                    PublicChatDto dto = new PublicChatDto();
                    dto.setId(entity.getId());
                    dto.setGroupName(entity.getName());
                    dto.setEncodedImage(encodedImageService.encodedImage(entity.getImage()));
                    dto.setCreatedAt(entity.getCreatedAt());
                    dto.setUserDto(userService.userEntitiesToDto(entity.getUserEntity()));
                    return dto;
                }).toList();
    }

public void createPublicChat(PublicChatDto publicChatDto) throws IOException {

    PublicChatEntity publicChatEntity = publicChatDtoToEntity(publicChatDto);
    publicChatRepository.save(publicChatEntity);
}



public void addFriendToGroup(FriendToGroupDto friendToGroupDto) {
    PublicChatEntity publicChatEntity = publicChatRepository.findById(friendToGroupDto.getGroupId()).get();

    UserEntity userEntity = userRepository.findById(friendToGroupDto.getFriendId()).get();
    publicChatEntity.getUserEntity().add(userEntity);
    publicChatRepository.save(publicChatEntity);
    eventPublisher.publishEvent(new FriendJoinedTheGroupEvent(this, publicChatEntity, userEntity));
    }

    }

