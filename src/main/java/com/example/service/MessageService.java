package com.example.service;

import com.example.exception.*;
import com.example.repository.MessageRepository;
import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountService accountService;
    
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }
    
    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank()) {
            throw new BadRequestException("Message text cannot be blank");
        }
        
        if (message.getMessageText().length() > 255) {
            throw new BadRequestException("Message text cannot exceed 255 characters");
        }
        
        if (!accountService.accountExists(message.getPostedBy())) {
            throw new ResourceNotFoundException("Account", "id", message.getPostedBy());
        }
    
        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public Message getMessageById(Integer messageId) {
        
        return messageRepository.findById(messageId)
                .orElse(null);
    }
    
    public Integer deleteMessageById(Integer messageId) {
        
        if (!messageRepository.existsById(messageId)) {
            return 0;
        }
        
        messageRepository.deleteById(messageId);
        return 1;
    }
    
    public Integer updateMessageText(Integer messageId, String newMessageText) {
        if (newMessageText.isBlank()) {
            throw new BadRequestException("Message text cannot be blank");
        }
        
        if (newMessageText.length() > 255) {
            throw new BadRequestException("Message text cannot exceed 255 characters");
        }
        
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }
    
    public List<Message> getMessagesByAccountId(Integer accountId) {

        if (!accountService.accountExists(accountId)) {
            throw new ResourceNotFoundException("Account", "id", accountId);
        }
        
        return messageRepository.findByPostedBy(accountId);
    }
}