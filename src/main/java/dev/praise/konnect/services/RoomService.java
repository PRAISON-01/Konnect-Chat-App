package dev.praise.konnect.services;

import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;

public interface RoomService {

    CreateRoomResponse createRoom(CreateRoomRequest request);
    void purgeExpiredRooms();
}
