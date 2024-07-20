package com.application.friendhub.websocket.event;

import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class FriendJoinedTheGroupEvent extends ApplicationEvent {
    private final PublicChatEntity publicChatEntity;
    private final UserEntity userEntity;


    public FriendJoinedTheGroupEvent(Object source, PublicChatEntity publicChatEntity, UserEntity userEntity) {
        super(source);
        this.publicChatEntity = publicChatEntity;
        this.userEntity = userEntity;

    }
}
