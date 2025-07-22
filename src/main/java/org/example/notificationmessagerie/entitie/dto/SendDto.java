package org.example.notificationmessagerie.entitie.dto;


import lombok.Data;

@Data
public class SendDto {
    public Long from, to;
    public String content;
    private String senderId;
    private String receiverId;
}