package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.RoomRepository;
import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class
RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    public void startWihThis() {
        roomService = new RoomServiceImpl();
    }

    @Test
    public void testThat_createstRoomWithValidRoomCode(){
        Mockito.when(roomRepository.findByRoomCode(any(String.class))).thenReturn(Optional.empty());

        CreateRoomRequest request = new CreateRoomRequest();
        request.setRoomName("test_room_name");
        CreateRoomResponse response = roomService.createRoom(request);

        assertThat(response).isNotNull();
        assertThat(response.getRoomCode()).isNotNull();

        assertThat(response.getRoomCode()).matches("^[A-Z0-9]{3}-[A-Z0-9]{3}$");
        assertThat(response.getCreatedAt()).isNotNull();
    }



}
