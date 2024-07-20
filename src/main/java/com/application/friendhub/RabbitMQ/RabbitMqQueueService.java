package com.application.friendhub.RabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqQueueService {

    private final RabbitAdmin rabbitAdmin;

    private final RabbitMQConfig rabbitMQConfig;

    public RabbitMqQueueService(RabbitAdmin rabbitAdmin, RabbitMQConfig rabbitMQConfig) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public void addNewQueue(String queueName, boolean isPublic) {
        Queue queue = rabbitMQConfig.createQueue(queueName);
        rabbitAdmin.declareQueue(queue);

        TopicExchange exchange;
        if (isPublic) {
            exchange = rabbitMQConfig.publicExchange();
        } else {
            exchange = rabbitMQConfig.privateExchange();
        }

        rabbitAdmin.declareBinding(rabbitMQConfig.createBinding(queue, exchange, queueName));
    }
}