package com.application.friendhub.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic","/queue");
     /*   registry.setUserDestinationPrefix("/user");*/ //s


/*
        registry.enableStompBrokerRelay("/queue").setRelayHost("moose.rmq.cloudamqp.com").setRelayPort(5672).setClientLogin("cgelrlqv")
                .setClientPasscode("GiNECvP6jLcC4osV0XuXl5O0OyAAcAoU");
*/

    }


}
