package com.application.friendhub.websocket.event;

import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
class FriendJoinedTheGroupEventTest {

private PublicChatEntity publicChatEntity;
private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        publicChatEntity = new PublicChatEntity();

    }


    @Test
    void shouldCheckIfEventTakesParameters() {
        FriendJoinedTheGroupEvent event = new FriendJoinedTheGroupEvent(FriendJoinedTheGroupEvent.class, publicChatEntity, userEntity);
        Assertions.assertNotNull(event);
        assertEquals(publicChatEntity, event.getPublicChatEntity());
        assertEquals(userEntity, event.getUserEntity());



    }
}