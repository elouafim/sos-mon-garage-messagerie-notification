package org.example.notificationmessagerie.service.imp;

import org.example.notificationmessagerie.entitie.Message;
import org.example.notificationmessagerie.repository.MessageRepository;
import org.example.notificationmessagerie.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private SimpMessagingTemplate ws;

    @Override
    public Message send(String sender, String reciver, String text) {
        Message m = new Message();
        m.setSenderId(sender);
        m.setReceiverId(reciver);
        m.setContent(text);
        repository.save(m);

        /*ws.convertAndSendToUser(
                to.toString(), "/queue/messages", m);*/
        ws.convertAndSend("/queue/messages", m);

        return m;
    }

    @Override
    public List<Message> history(String senderId, String receiverId) {
        return repository.findBySenderIdAndReceiverId(senderId,receiverId);
    }

    @Override
    public List<Message> getAllMessage(String senderId, String receiverId) {
        return repository.findConversation(senderId,receiverId);
    }

    @Override
    public Message markAsRead(Long messageId) {
        Message message = repository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));

        message.setRead(true);
        return repository.save(message);
    }

}
