package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.dtos.request.SendMessageRequest;
import dev.praise.konnect.dtos.response.SendMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private AliasGeneratorService aliasGeneratorService;

    @InjectMocks
    private MessageServiceImpl messageService;



    private Message newMessage;

    private Room room;
    
    @BeforeEach
    void startWithThis() {

         room = new Room();
         room.setId(1L);
         room.setCreatedAt(LocalDateTime.now());
         room.setRoomName("test_room");
         room.setRoomCode("ABC-123");

         newMessage = new Message(1L,room, "test_senderalias", "test_client_id", "Hello World", LocalDateTime.now());

        newMessage.setClientId("Client_ID");
        newMessage.setContent("Hello World");
        newMessage.setCreatedAt(LocalDateTime.now());
        newMessage.setId(1L);
        newMessage.setRoom(room);
        newMessage.setSenderAlias("test_senderalias");
    }

    @Test
    @DisplayName("test that message service an send and return message")
    void newMessageService_sendMessage_messageSaves() {
        Mockito.when(aliasGeneratorService.generateAlias()).thenReturn("test_senderalias");
        Mockito.when(messageRepository.save(any(Message.class))).thenReturn(newMessage);


         Message message = messageService.sendMessage(newMessage);

        assertNotNull(message);
        assertEquals("Hello World", message.getContent());
        assertEquals("Client_ID", message.getClientId());
        assertEquals("test_senderalias", message.getSenderAlias());

        Mockito.verify(aliasGeneratorService, Mockito.times(1)).generateAlias();
        Mockito.verify(messageRepository, Mockito.times(1)).save(any(Message.class));

    }

    @Test
    @DisplayName("test that messageservice can retrieve all messages")
    void newMessageService_sendMessage_messageSaves_canFetchAllMessaged() {
        List<Message> expectedHistory = List.of(newMessage);
        Mockito.when(messageRepository.findByRoomIdOrderByCreatedAtDesc(1L)).thenReturn(expectedHistory);

        List<Message> actualHistory = messageService.getMessagesByRoom(room);

        assertFalse(actualHistory.isEmpty());
        assertEquals(1, actualHistory.size());
        assertEquals("Hello World", actualHistory.get(0).getContent());

        Mockito.verify(messageRepository, Mockito.times(1)).findByRoomIdOrderByCreatedAtDesc(1L);
    }


}
