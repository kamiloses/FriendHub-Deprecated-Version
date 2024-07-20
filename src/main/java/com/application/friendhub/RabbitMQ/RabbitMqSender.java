package com.application.friendhub.RabbitMQ;

import com.application.friendhub.Entity.MessagesEntity;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqSender {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    public void sendToRabbitWithQueueName(String queueName, Long messageId) {
        rabbitTemplate.convertAndSend(queueName, messageId);
      log.info("Message sent to  queue");


    }
}