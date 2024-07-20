package com.application.friendhub.loggedUser.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncodedImageService {


    public String encodedImage(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

}






