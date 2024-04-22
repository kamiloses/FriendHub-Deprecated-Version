package com.application.friendhub.EmailSender;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class EmailServiceImpl {

   public Logger LOG =LoggerFactory.getLogger(getClass());
   private Resource resource;

 /*   private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String toEmail, String token) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("FriendHub_Team@gmail.com");
        message.setTo(toEmail);
        *//*message.setText();*//*
        message.setSubject("Verification Token");

        javaMailSender.send(message);
        LOG.info("Email has been sent to the user");


    }*/
    private String contentOfTheEmail(String toEmail,String token) throws IOException {
    DefaultResourceLoader loader = new DefaultResourceLoader();
    String contentAsString = loader.getResource("mailContent/mailContent").getContentAsString(StandardCharsets.UTF_8);
  return   contentAsString.replace("[User Name]", toEmail.substring(0, toEmail.indexOf("@"))).replace("[token]", token);


}

    public   String generateToken() {
        UUID uuid = UUID.randomUUID();


        return uuid.toString().substring(0, 5).toUpperCase();
    }


    }
