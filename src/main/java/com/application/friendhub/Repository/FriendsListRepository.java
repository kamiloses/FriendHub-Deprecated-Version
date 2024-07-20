package com.application.friendhub.Repository;

import com.application.friendhub.Entity.FriendsListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsListRepository extends JpaRepository<FriendsListEntity,Long> {


    FriendsListEntity findByConnectionToYourOwnAccount_IdAndUserEntity_Id(Long connectionToYourOwnAccount_Id, Long userId);

}
