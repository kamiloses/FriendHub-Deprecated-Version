package com.application.friendhub.Repository;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.h2.engine.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    private  UserEntity user1;
    private  UserEntity user2;
    @Autowired
    private UserRepository userRepository;




    private List<TimelineEntity> allPostsOfUser1() {
        TimelineEntity post1 = new TimelineEntity();
        TimelineEntity post2 = new TimelineEntity();
        TimelineEntity post3 = new TimelineEntity();
        post1.setUser(user1);
        post2.setUser(user1);
        post3.setUser(user1);


        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.persist(post3);

        return List.of(post1, post2, post3);
    }

    private List<TimelineEntity> allPostsOfUser2() {
        TimelineEntity post1 = new TimelineEntity();
        TimelineEntity post2 = new TimelineEntity();
        TimelineEntity post3 = new TimelineEntity();

        post1.setUser(user2);
        post2.setUser(user2);
        post3.setUser(null);


        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.persist(post3);

        return List.of(post1, post2, post3);
    }






    @Test
    public void shouldCheckIfNumberOfUserPostsIsCorrect1() {
        user1= new UserEntity();
        user1.setEmail("kamiloses04@gmail.com");
        user1.setPassword("kamil123");
        user1.setTimelines(allPostsOfUser1());
        entityManager.persistAndFlush(user1);



        List<TimelineEntity> allUsersPost = userRepository.findAllTimelineEntityByEmail(user1.getEmail());
        Assertions.assertEquals(3, allUsersPost.size());
    }






    @Test
    public void should_CheckIfNumberOfUserPostsIsCorrect2() {
        user2= new UserEntity();
        user2.setEmail("kamiloses04@gmail.com");
        user2.setPassword("kamil123");
        user2.setTimelines(allPostsOfUser2());
        entityManager.persistAndFlush(user2);

        List<TimelineEntity> allUsersPost = userRepository.findAllTimelineEntityByEmail(user2.getEmail());
        Assertions.assertEquals(2,allUsersPost.size());
    }


}