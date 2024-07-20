package com.application.friendhub.Repository;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class MessageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    private PrivateChatEntity privateChat1;
    private PrivateChatEntity privateChat2;


    private MessagesEntity message1;
    private MessagesEntity message2;
    private MessagesEntity message3;
    private MessagesEntity message4;

    @BeforeEach
    void setUp() {
//todo popraw czytelnosc

        message1 = new MessagesEntity();
        message2 = new MessagesEntity();
        message3 = new MessagesEntity();
        message4 = new MessagesEntity();

        privateChat1 = new PrivateChatEntity();
        privateChat2 = new PrivateChatEntity();


        privateChat1.setMessages(List.of(message1, message2, message3));
        privateChat2.setMessages(List.of(message4));


        message1.setPrivateChats_id(privateChat1);
        message2.setPrivateChats_id(privateChat1);
        message3.setPrivateChats_id(privateChat1);
        message4.setPrivateChats_id(privateChat2);


        entityManager.persist(privateChat1);
        entityManager.persist(privateChat2);


        entityManager.persistAndFlush(message1);
        entityManager.persistAndFlush(message2);
        entityManager.persistAndFlush(message3);
        entityManager.persistAndFlush(message4);


    }

    @Test//todo dodaj więcej assercji
    void findAllMessagesRelatedWithPrivateChat() {
        List<MessagesEntity> result = messageRepository.findAllMessagesRelatedWithPrivateChat(
                List.of(privateChat1.getId(), privateChat2.getId()));

        Assertions.assertEquals(4, result.size());
    }
}