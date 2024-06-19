package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.TimelineEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
public class TimelineImagesDto {


    private Long id;

    private Date dateOfPublication;

    private MultipartFile timelineImage;

    private TimelineEntity timelineEntity;
}
