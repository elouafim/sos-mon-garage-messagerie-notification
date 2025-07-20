package org.example.notificationmessagerie.service;

import org.example.notificationmessagerie.entitie.Message;

import java.time.Instant;
import java.util.List;

public interface IMessageService {

    public Message send(Long from, Long to, String text);
    public List<Message> history(Long from, Long to, Instant since);
    public void markAsRead(Long messageId, Long userId);

}
