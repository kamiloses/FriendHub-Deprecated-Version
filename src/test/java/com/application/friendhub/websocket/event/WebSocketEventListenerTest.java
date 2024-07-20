package com.application.friendhub.websocket.event;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class WebSocketEventListenerTest {




//todo ogarnij testowanie socketów i rabbita

    @Test
    public void testHandleWebSocketConnectListener() {
        Principal principal= mock(Principal.class);
         when(principal.getName()).thenReturn("abc@gmail.com");

        SimpMessageHeaderAccessor simpMessageHeaderAccessor = StompHeaderAccessor.create();
        simpMessageHeaderAccessor.setUser(principal);
//        SessionDisconnectEvent event=mock(SessionDisconnectEvent.class);
//        when(event.getMessage()).thenReturn(simpMessageHeaderAccessor.getMessageHeaders());

    }





}