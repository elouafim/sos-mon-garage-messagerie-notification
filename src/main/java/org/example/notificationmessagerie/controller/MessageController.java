package org.example.notificationmessagerie.controller;


import org.example.notificationmessagerie.entitie.Message;
import org.example.notificationmessagerie.entitie.dto.SendDto;
import org.example.notificationmessagerie.service.imp.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService svc;

    @Autowired
    private SimpMessagingTemplate ws;

    @GetMapping("/test")
    public String test() {
        ws.convertAndSend("/queue/messages", "bonjour tout le monde");
        return  "app work!";
    }

    @PostMapping
    public Message send(@RequestBody SendDto dto) {
        return svc.send(dto.getSenderId(), dto.getReceiverId(), dto.content);
    }

    @GetMapping
    public List<Message> history(@RequestParam String senderId, @RequestParam String receiverId) {
        return svc.history(senderId, receiverId);
    }

    @PutMapping("/mark-as-read")
    public ResponseEntity<?> markAsRead(@RequestParam Long idMessage) {
        try {
            return ResponseEntity.ok(svc.markAsRead(idMessage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

