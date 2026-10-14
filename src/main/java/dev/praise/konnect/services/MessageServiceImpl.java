package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AliasGeneratorService aliasGeneratorService;


    @Override
    public Message sendMessage(Message message) {
        String aliasGeneratorResponse = aliasGeneratorService.generateAlias();
        message.setSenderAlias(aliasGeneratorResponse);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByRoom(Room room) {
        return messageRepository.findByRoomIdOrderByCreatedAtDesc(room.getId());
    }
}
