package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.dtos.request.DeleteMessageRequest;
import dev.praise.konnect.dtos.response.DeleteMessageResponse;
import dev.praise.konnect.exceptions.MessageNotFoundException;
import org.hibernate.sql.Delete;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getMessagesByRoom(Room room);
    DeleteMessageResponse deleteMessage(DeleteMessageRequest request) throws MessageNotFoundException;
}
