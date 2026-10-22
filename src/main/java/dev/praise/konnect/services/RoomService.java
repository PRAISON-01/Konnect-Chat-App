package dev.praise.konnect.services;

import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;
import dev.praise.konnect.exceptions.RoomNotFoundException;

public interface RoomService {

    CreateRoomResponse createRoom(CreateRoomRequest request);
    void purgeExpiredRooms();
    Room getRoomByCode(String roomCode) throws RoomNotFoundException;
}
