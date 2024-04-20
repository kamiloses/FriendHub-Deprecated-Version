package com.application.friendhub.EmailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.File;

public class EmailServiceImpl{

      Logger LOG;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        LOG = LoggerFactory.getLogger(getClass()); //sprawdzić
    }

    public void sendEmail(String toEmail, String token) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("FriendHub_Team@gmail.com");
        message.setTo(toEmail);
        /*message.setText();*/
        message.setSubject("Verification Token");

        javaMailSender.send(message);
        LOG.info("Email has been sent to the user");



    }

}
