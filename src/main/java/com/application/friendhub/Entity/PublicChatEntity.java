package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"userEntity", "image"})
public class PublicChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    @Column(name = "group_picture", columnDefinition = "LongBlob")
    private byte[] image;

    private Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "user_public_chat",
            joinColumns = @JoinColumn(name = "public_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> userEntity;

    @OneToMany(mappedBy = "publicChats_id", cascade = CascadeType.REMOVE)
    private List<MessagesEntity> messages;
}
