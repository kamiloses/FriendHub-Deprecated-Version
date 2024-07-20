package com.application.friendhub.websocket.config;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Data
@NoArgsConstructor
@Service
public class AvailableUserService {

    //todo zamień na enuma
    private HashMap<Long, String> onlineUsers = new HashMap<Long, String>();

    private Long id;
    private String status;

    public AvailableUserService(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public void userConnected(Long userId) {
        onlineUsers.put(userId, "CONNECTED");
    }

    public void userDisconnected(Long userId) {
        onlineUsers.put(userId, "DISCONNECTED");
    }


    public boolean isOnline(Long userId) {


        return "CONNECTED".equals(onlineUsers.get(userId));
    }

    @PreDestroy
    public void cleanUp() {
        onlineUsers.clear();

    }


}




