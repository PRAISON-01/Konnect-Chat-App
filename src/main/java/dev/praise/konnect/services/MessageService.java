package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getMessagesByRoom(Room room);
}
