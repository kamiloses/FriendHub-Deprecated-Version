package com.application.friendhub.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PRIVATE_EXCHANGE = "private_exchange";
    public static final String PUBLIC_EXCHANGE = "public_exchange";

    @Bean
    public TopicExchange privateExchange() {
        return new TopicExchange(PRIVATE_EXCHANGE);
    }

    @Bean
    public TopicExchange publicExchange() {
        return new TopicExchange(PUBLIC_EXCHANGE);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    public Queue createQueue(String queueName) {
        return new Queue(queueName, true);
    }

    public Binding createBinding(Queue queue, TopicExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}