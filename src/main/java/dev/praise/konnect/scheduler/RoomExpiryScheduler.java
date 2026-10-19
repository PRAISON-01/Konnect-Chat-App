package dev.praise.konnect.scheduler;

import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.data.repositories.RoomRepository;
import dev.praise.konnect.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RoomExpiryScheduler {

    private static final Logger log = LoggerFactory.getLogger(RoomExpiryScheduler.class);

    private final RoomService roomService;

    public RoomExpiryScheduler(RoomService roomService){
       this.roomService = roomService;
    }


    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void executeDailyCleanup() {
        roomService.purgeExpiredRooms();
    }
}
