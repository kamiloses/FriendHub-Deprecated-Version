package com.application.friendhub.websocket.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableUserService {

    private Long id;
    private String status;


/*    private final Map<Long, String> userMap = new HashMap<>();*/






    }



/*

    public void addUserOrRemove(Long id, String status) {
      */
/*  userMap.put(id, status);*//*


        if (!userMap.containsKey(id)) {
            userMap.put(id, status);
        }
        else userMap.remove(id,status);


    }


    public String getUserId(Long id) {
        return userMap.get(id);
    }

    public void removeUser(Long id,String status) {
        userMap.remove(id, status);
    }


*/




