package org.example.notificationmessagerie.repository;

import org.example.notificationmessagerie.entitie.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdAndTimestampAfter(
            String senderId, String receiverId, Instant since);
}