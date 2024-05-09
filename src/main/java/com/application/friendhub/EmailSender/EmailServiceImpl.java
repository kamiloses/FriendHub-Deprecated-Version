package com.application.friendhub.EmailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Lazy
public class EmailServiceImpl {

   public Logger LOG =LoggerFactory.getLogger(getClass());


    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }



    public void sendEmail(String toEmail, String token) throws IOException {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("friendhub.confirm@gmail.com");
        message.setTo("kamil.kurzaj04@gmail.com");
        message.setText(contentOfTheEmail("kamil.kurzaj04@gmail.com","33333"));
        message.setSubject("Verification Token");

        javaMailSender.send(message);
        LOG.warn("Email has been sent to the user");


    }
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
