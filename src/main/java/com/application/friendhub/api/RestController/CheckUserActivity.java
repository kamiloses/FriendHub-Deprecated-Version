package com.application.friendhub.api.RestController;

import com.application.friendhub.api.dto.CommentsApiDto;
import com.application.friendhub.api.service.ApiService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CheckUserActivity {

    private final ApiService apiService;

    public CheckUserActivity(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/activity/{userEmail}")
    public ResponseEntity<String> checkUserActivityPer(@RequestHeader int days, @PathVariable String userEmail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Authenticated-User",SecurityContextHolder.getContext().getAuthentication().getName());


        return    new ResponseEntity<>("this is number of user's comment in specific time:"+apiService.userActivity(userEmail,days),httpHeaders, HttpStatus.OK);

    }


    @GetMapping("/comments/{userEmail}")
    public ResponseEntity<List<CommentsApiDto>> checkUserComments(@PathVariable String userEmail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Authenticated-User",SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseEntity<>(apiService.allUserComments(userEmail),httpHeaders,HttpStatus.OK);
    }


}

    //todo dokończ
//    public int sreiaLiczbaLikówPostówuzytkownika() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow();
//        List<TimelineEntity> timelineEntityByUserId = timelineRepository.findTimelineEntityByUser_Id(userEntity.getId());
//
//        findLikeEntityByLike_Entity_id(timelineEntityByUserId);
//
//
//
//
//
//*/
//
//
//}