package com.application.friendhub.Repository;

import com.application.friendhub.Entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {

     UserDetailsEntity findUserDetailsEntityByUserEntity_Email(String email);

     List<UserDetailsEntity> findUserDetailsEntitiesByFirstNameOrLastName(String firstName,String lastName);






}
