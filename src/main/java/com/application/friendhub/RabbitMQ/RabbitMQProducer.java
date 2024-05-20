package com.application.friendhub.RabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQProducer {
    private RabbitTemplate  rabbitTemplate;



    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    @Value("${rabbitmq.routing_key.name}")
    private String routingKey;






    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(String message) {
    log.info("Sending message to RabbitMQ" +message);

    rabbitTemplate.convertAndSend(exchange, routingKey, message);

    }

}

