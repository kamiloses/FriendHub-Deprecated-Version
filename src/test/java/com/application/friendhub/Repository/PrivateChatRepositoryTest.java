package com.application.friendhub.Repository;

import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class PrivateChatRepositoryTest {
    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UserEntity userEntity1;
    private UserEntity userEntity2;
    private UserEntity userEntity3;

    @BeforeEach
    void setUp() {
        userEntity1 = new UserEntity();
        userEntity2 = new UserEntity();
        userEntity3 = new UserEntity();

        entityManager.persist(userEntity1);
        entityManager.persist(userEntity2);
        entityManager.persist(userEntity3);

        PrivateChatEntity privateChatEntity1 = new PrivateChatEntity();
        privateChatEntity1.setUser1(userEntity1);
        privateChatEntity1.setUser2(userEntity2);
        entityManager.persistAndFlush(privateChatEntity1);

        PrivateChatEntity privateChatEntity2 = new PrivateChatEntity();
        privateChatEntity2.setUser1(userEntity2);
        privateChatEntity2.setUser2(userEntity3);
        entityManager.persistAndFlush(privateChatEntity2);
    }

    @Test
    void testFindPrivateChatEntitiesByUser1AndUser2() {
        PrivateChatEntity privateChat1 = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(userEntity1.getId(), userEntity2.getId());
        PrivateChatEntity privateChat2 = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(userEntity2.getId(), userEntity1.getId());
        PrivateChatEntity privateChat3 = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(userEntity2.getId(), userEntity2.getId());
        PrivateChatEntity privateChat4 = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(userEntity2.getId(), userEntity3.getId());
        PrivateChatEntity privateChat5 = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(userEntity1.getId(), userEntity3.getId());

        Assertions.assertNotNull(privateChat1);
        Assertions.assertNotNull(privateChat2);
        Assertions.assertNull(privateChat3);
        Assertions.assertNotNull(privateChat4);
        Assertions.assertNull(privateChat5);
    }
}