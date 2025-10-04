package org.example.notificationmessagerie.repository;

import org.example.notificationmessagerie.entitie.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.receiverId = :receiverId AND m.read = false")
    List<Message> findBySenderIdAndReceiverId(
            @Param("senderId") String senderId,
            @Param("receiverId") String receiverId);

    @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.receiverId = :receiverId ORDER BY m.id DESC")
    List<Message> findBySenderIdAndReceiverId2(
            @Param("senderId") String senderId,
            @Param("receiverId") String receiverId);


    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :senderId AND m.receiverId = :receiverId) " +
            "OR (m.senderId = :receiverId AND m.receiverId = :senderId) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("senderId") String senderId,
                                   @Param("receiverId") String receiverId);
}
