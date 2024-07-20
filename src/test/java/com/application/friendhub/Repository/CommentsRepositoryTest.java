package com.application.friendhub.Repository;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.loggedUser.service.DateConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentsRepositoryTest {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DateConverterService dateConverterService;

    private CommentsEntity comment1;
    private CommentsEntity comment2;
    private CommentsEntity comment3;
    private CommentsEntity comment4;

    private UserEntity user1;
    private UserEntity user2;



    //todo dokończ
//    @BeforeEach
 //   void setUp() {
//        user1=new UserEntity();
//        user2=new UserEntity();
//
//
//
//        comment1 = new CommentsEntity();
////        comment1.setUserEntity(user1);
//        comment1.setDateOfPublication(dateConverterService.convert(LocalDate.now().minusMonths(5)));
//
//         comment2 = new CommentsEntity();
////        comment2.setUserEntity(user1);
//        comment2.setDateOfPublication(dateConverterService.convert(LocalDate.now().minusDays(15)));
//
//         comment3 = new CommentsEntity();
////        comment3.setUserEntity(user1);
//        comment3.setDateOfPublication(dateConverterService.convert(LocalDate.now().minusYears(5)));
//
//         comment4 = new CommentsEntity();
////        comment4.setUserEntity(user1);
//        comment4.setDateOfPublication(dateConverterService.convert(LocalDate.now()));
//
//         user1.setCommentEntity(List.of(comment1,comment2,comment3));
//
//
//        entityManager.persistAndFlush(comment1);
//        entityManager.persistAndFlush(comment2);
//        entityManager.persistAndFlush(comment3);
//        entityManager.persistAndFlush(comment4);
//
//
//    }
//
//    @Test
//    void should_Check_If_There_Is_Correct_Date_Searching() {
////commentsRepository.findCommentsEntitiesByUserIdInSpecificTime(user1.getId();
//
//    }


}