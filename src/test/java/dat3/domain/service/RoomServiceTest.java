package dat3.domain.service;

import dat3.domain.dto.HotelResponse;
import dat3.domain.dto.RoomRequest;
import dat3.domain.dto.RoomResponse;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Room;
import dat3.domain.repository.HotelRepository;
import dat3.domain.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoomServiceTest {

    @Autowired
    RoomRepository roomRepository;
    RoomService roomService;

    @Autowired
    HotelRepository hotelRepository;

    Room r1, r2;
    Hotel h1, h2;

    boolean isInitialized = false;
    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            r1 = new Room();
            r1.setRoomNumber(1);
            r2 = new Room();
            r2.setRoomNumber(1);
            roomRepository.save(r1);
            roomRepository.save(r2);
            h1 = new Hotel();
            h1.setRooms(List.of(r1));
            h2 = new Hotel();
            h1.setRooms(List.of(r2));
            hotelRepository.save(h1);
            hotelRepository.save(h2);
            roomService = new RoomService(roomRepository, hotelRepository);
            isInitialized = true;
        }
    }

    @Test
    void findById() {
        r1.setRoomDescription("test");
        RoomResponse response = roomService.findById(r1.getId(), false);
        assertEquals("test", response.getRoomDescription());
    }

    @Test
    void addRoomSucces() {
        RoomRequest r3 = new RoomRequest();
        r3.setRoomNumber(3);
        r3.setHotelId(h1.getId());
        roomService.addRoom(r3, false);
        assertEquals(3, roomRepository.count());
    }

    @Test
    void deleteRoom() {
        roomService.deleteRoom(r1.getId());
        assertEquals(1, roomRepository.count());
    }
}