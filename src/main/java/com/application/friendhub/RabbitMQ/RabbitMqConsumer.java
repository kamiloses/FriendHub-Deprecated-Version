package com.application.friendhub.RabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Slf4j
public class RabbitMqConsumer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }




    public LinkedList<Long> receiveMessage(String queueName) {
        LinkedList<Long> rabbitMessages = new LinkedList<>();
        Long message;
        while ((message = (Long)rabbitTemplate.receiveAndConvert(queueName)) != null) {
            rabbitMessages.add(message);
        }
        log.info("message receiver from queue");
        return rabbitMessages;
    }


}