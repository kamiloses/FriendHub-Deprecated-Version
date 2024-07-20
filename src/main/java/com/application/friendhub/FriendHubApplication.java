package com.application.friendhub;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@SpringBootApplication
@EnableRabbit
public class FriendHubApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FriendHubApplication.class, args);


	}

}



