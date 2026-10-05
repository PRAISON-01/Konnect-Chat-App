package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.RoomRepository;
import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    private static final String CHARACTERS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public CreateRoomResponse createRoom(CreateRoomRequest request) {

        String code ;
        do{
            code = generateRoomCode();
        } while(roomRepository.findByRoomCode(code).isPresent());

        Room room = new Room();
        room.setRoomCode(code);
        room.setCreatedAt(LocalDateTime.now());
        room.setRoomName(request.getRoomName());
        return new CreateRoomResponse();
    }

    private String generateRoomCode() {
        StringBuilder sb = new StringBuilder(7);
        for(int count =0; count < 6; count++) {
            if(count == 3) {
                sb.append("-");
            }

            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
