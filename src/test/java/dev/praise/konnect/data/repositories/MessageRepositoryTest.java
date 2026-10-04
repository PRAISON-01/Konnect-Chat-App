package dev.praise.konnect.data.repositories;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Room sharedRoom;

    @BeforeEach
    public void startWithThis() {
        sharedRoom = new Room();
        sharedRoom.setRoomCode("XYZ-789");
        sharedRoom.setCreatedAt(LocalDateTime.now());
        roomRepository.save(sharedRoom);
    }

    @Test
    public void testThat_createMessages_orderByRecentFirst() {
        Message oldMessage = new Message(null, sharedRoom, "user_one", "test_uuid_1", "Hello First", LocalDateTime.now());
        Message newMessage = new Message(null, sharedRoom, "user_one", "test_uuid_1", "Hello Second", LocalDateTime.now());

        messageRepository.save(oldMessage);
        messageRepository.save(newMessage);


        List<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(sharedRoom.getId());

        assertThat(messages.size()).isEqualTo(2);
        assertThat(messages.get(0).getContent()).isEqualTo("Hello Second");
        assertThat(messages.get(1).getContent()).isEqualTo("Hello First");
    }

    @Test
    void shouldCountMessagesSentAfterSpecificTimestampForRateLimiting() {
        String clientId = "test_uuid";
        LocalDateTime thirtySecondsAgo = LocalDateTime.now().minusSeconds(30);

        Message oldMessage = new Message(null, sharedRoom, "user_one", clientId, "Old Message", LocalDateTime.now().minusSeconds(60));
        Message messageOne = new Message(null, sharedRoom, "user_one", clientId, "First Message", LocalDateTime.now().minusSeconds(10));
        Message messageTwo = new Message(null, sharedRoom, "user_one", clientId, "Second Message", LocalDateTime.now());

        messageRepository.save(oldMessage);
        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);

        long messageCount = messageRepository.countByClientIdAndCreatedAtAfter(clientId, thirtySecondsAgo);

        assertThat(messageCount).isEqualTo(2);
    }

}
