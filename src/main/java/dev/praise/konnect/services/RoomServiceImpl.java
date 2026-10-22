package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.data.repositories.RoomRepository;
import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;
import dev.praise.konnect.exceptions.RoomNotFoundException;
import dev.praise.konnect.scheduler.RoomExpiryScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomServiceImpl(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(RoomExpiryScheduler.class);

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

        Room savedRoom = roomRepository.save(room);

        CreateRoomResponse response = new CreateRoomResponse();

        response.setRoomCode(savedRoom.getRoomCode());
        response.setRoomName(savedRoom.getRoomName());
        response.setExpiresAt(savedRoom.getCreatedAt().plusDays(7));
        response.setCreatedAt(savedRoom.getCreatedAt());
        return response;
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

    @Override
    public void purgeExpiredRooms() {
        log.info("Business Logic Layer: Calculating thresholds for room eviction...");

        LocalDateTime sevenDaysThreshold = LocalDateTime.now().minusDays(7);

        int deletedMessages = messageRepository.deleteByCreatedAtBefore(sevenDaysThreshold);
        int deletedRooms = roomRepository.deleteByCreatedAtBefore(sevenDaysThreshold);

        if (deletedRooms > 0 || deletedMessages > 0)  {
            log.info("ROuntine maintenance complete: Evicted {} expired rooms and {} associated chat logs.",
                    deletedRooms, deletedMessages);
        } else {
            log.info("Routine maintenance complete: No rooms found older than 7 days.");
        }
    }

    @Override
    public Room getRoomByCode(String roomCode) throws RoomNotFoundException {
        java.util.Optional<Room> optionalRoom = roomRepository.findByRoomCode(roomCode);

        if (optionalRoom.isEmpty()) {
            throw new RoomNotFoundException("Room not found with code: " + roomCode);
        }

        Room room = optionalRoom.get();

        return room;
    }
}
