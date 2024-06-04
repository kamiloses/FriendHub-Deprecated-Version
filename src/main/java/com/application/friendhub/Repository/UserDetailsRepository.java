package com.application.friendhub.Repository;

import com.application.friendhub.Entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {

     UserDetailsEntity findUserDetailsEntityByUserEntity_Email(String email);


     @Query("SELECT u FROM UserDetailsEntity u WHERE (u.firstName = :firstName OR u.lastName = :lastName) AND u.userEntity.id != :userId")
     List<UserDetailsEntity> findUserDetailsEntitiesByFirstNameOrLastName( @Param("firstName") String firstName,
                                                                           @Param("lastName") String lastName,
                                                                            @Param("userId") Long userId);






}
