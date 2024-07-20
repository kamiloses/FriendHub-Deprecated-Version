package com.application.friendhub.RabbitMQ;

import com.application.friendhub.Entity.MessageRecipientEntity;
import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.MessageRecipientRepository;
import com.application.friendhub.Repository.MessageRepository;
import com.application.friendhub.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class RabbitService {
private final UserRepository userRepository;
private final RabbitMqConsumer rabbitMqConsumer;
private final MessageRepository messageRepository;
private final MessageRecipientRepository messageRecipientRepository;
    public RabbitService(UserRepository userRepository, RabbitMqConsumer rabbitMqConsumer, MessageRepository messageRepository, MessageRecipientRepository messageRecipientRepository) {
        this.userRepository = userRepository;
        this.rabbitMqConsumer = rabbitMqConsumer;
        this.messageRepository = messageRepository;
        this.messageRecipientRepository = messageRecipientRepository;
    }



    public void SaveReceivedMessagesDataToDatabase(String userId,String receiverEmail){
    UserEntity userEntity = userRepository.findUserEntityByEmail(receiverEmail).orElseThrow();
    //todo zaimplementuj asynchoricznosc by sockety nie czekały na dostarczenie wiadomosci do rabita
    LinkedList<Long> messageId = rabbitMqConsumer.receiveMessage(userId+":"+userEntity.getId());

    List<MessagesEntity> allReceivedMessage = messageRepository.findAllById(messageId);
    Date receivedMessage=new Date();
    for (int i = 0; i <allReceivedMessage.size() ; i++) {
        MessageRecipientEntity messageRecipientEntity = new MessageRecipientEntity();
        messageRecipientEntity.setRecipient(userEntity);
        messageRecipientEntity.setDateOfReceivedMessage(receivedMessage);
        messageRecipientEntity.setMessage(allReceivedMessage.get(i));
        messageRecipientRepository.save(messageRecipientEntity);

    }

}









}
