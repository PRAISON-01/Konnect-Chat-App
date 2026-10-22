package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.dtos.request.DeleteMessageRequest;
import dev.praise.konnect.dtos.response.DeleteMessageResponse;
import dev.praise.konnect.exceptions.MessageNotFoundException;
import dev.praise.konnect.exceptions.NotMessageOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Override
    public DeleteMessageResponse deleteMessage(DeleteMessageRequest request) throws MessageNotFoundException {
        Message message = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new MessageNotFoundException(String.valueOf(request.getMessageId())));

        if (!message.getClientId().equals(request.getClientId())) {
            throw new NotMessageOwnerException("You are not the owner of this message!");
        }

        messageRepository.delete(message);

        DeleteMessageResponse response = new DeleteMessageResponse();
        response.setMessageId(request.getMessageId());
        response.setContent("Message deleted");
        return response;
    }
}
