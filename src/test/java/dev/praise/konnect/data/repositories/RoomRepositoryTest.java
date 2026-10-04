package dev.praise.konnect.data.repositories;

import dev.praise.konnect.data.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testThat_RoomExist_FindByRoomCodeReturnsOptionalRoom() {
        Room room = new Room();
        room.setRoomCode("test_room_code");
        room.setCreatedAt(LocalDateTime.now());
        roomRepository.save(room);

        Optional<Room> foundRoom = roomRepository.findByRoomCode("test_room_code");
        assertThat(foundRoom).isPresent();

        assertThat(foundRoom.get().getRoomCode()).isEqualTo("test_room_code");
    }

    @Test
    public void testThat_RoomDoesNotExist_FindByRoomCodeReturnsOptionalRoom() {

        Optional<Room> foundRoom = roomRepository.findByRoomCode("test_room_code");
        assertThat(foundRoom).isNotPresent();

    }


}
