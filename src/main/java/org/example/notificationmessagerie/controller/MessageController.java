package org.example.notificationmessagerie.controller;


import org.example.notificationmessagerie.entitie.Message;
import org.example.notificationmessagerie.entitie.dto.SendDto;
import org.example.notificationmessagerie.service.imp.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return svc.send(dto.from, dto.to, dto.content);
    }

    @GetMapping
    public List<Message> history(@RequestParam Long with, @RequestParam String since) {
        Long me = null;
        return svc.history(me, with, Instant.parse(since));
    }
}

