package org.example.notificationmessagerie.service.imp;

import org.example.notificationmessagerie.entitie.Message;
import org.example.notificationmessagerie.repository.MessageRepository;
import org.example.notificationmessagerie.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private SimpMessagingTemplate ws;

    @Override
    public Message send(Long from, Long to, String text) {
        Message m = new Message();
        m.setReceiverId(to);
        m.setContent(text);
        repository.save(m);

        /*ws.convertAndSendToUser(
                to.toString(), "/queue/messages", m);*/
        ws.convertAndSend("/queue/messages", m);

        return m;
    }

    @Override
    public List<Message> history(Long from, Long to, Instant since) {
        return repository.findBySenderIdAndReceiverIdAndTimestampAfter(from, to, since);
    }

    @Override
    public void markAsRead(Long messageId, Long userId) {

        repository.findById(messageId).ifPresent(msg -> {
            if (msg.getReceiverId().equals(userId)) {
                msg.setRead(true);
                repository.save(msg);
            }
        });

    }
}
