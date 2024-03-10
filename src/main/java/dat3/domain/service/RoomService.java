package dat3.domain.service;

import dat3.domain.dto.RoomRequest;
import dat3.domain.dto.RoomResponse;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Room;
import dat3.domain.repository.HotelRepository;
import dat3.domain.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class RoomService {

    RoomRepository roomRepository;
    HotelRepository hotelRepository;
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public RoomResponse findById(int id, boolean includeAll) {
        Room room = getRoomById(id);
        return new RoomResponse(room, includeAll);
    }

    public RoomResponse addRoom(RoomRequest body, boolean b) {
        Hotel hotel = hotelRepository.findById(body.getHotelId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel with this id does not exist"));
        roomRepository.findAll().stream().filter(r -> r.getRoomNumber() == body.getRoomNumber() && r.getHotel().getId() == body.getHotelId()).findFirst().ifPresent(r -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room with this number already exists in this hotel");
        });
        Room room = roomRepository.save(new Room(body.getRoomNumber(), body.getFloor(), body.getNumberOfBeds(), body.getRoomType(), body.getRoomPrice(), body.getRoomDescription(), hotel));
        return new RoomResponse(room, b);
    }

    public ResponseEntity<Boolean> editRoom(RoomRequest body, int id) {
        Room room = getRoomById(id);
        Hotel hotel = room.getHotel();
        if (body.getHotelId() != hotel.getId()) {
            hotelRepository.findById(body.getHotelId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel with this id does not exist"));
        }
        if (body.getRoomNumber() != room.getRoomNumber() && roomRepository.findAll().stream().anyMatch(r -> r.getRoomNumber() == body.getRoomNumber() && r.getHotel().getId() == body.getHotelId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room with this number already exists in this hotel");
        }
        room.setRoomNumber(body.getRoomNumber());
        room.setFloor(body.getFloor());
        room.setNumberOfBeds(body.getNumberOfBeds());
        room.setRoomType(body.getRoomType());
        room.setRoomPrice(body.getRoomPrice());
        room.setRoomDescription(body.getRoomDescription());
        room.setEdited(LocalDateTime.now());
        roomRepository.save(room);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> deleteRoom(int id) {
        Room room = getRoomById(id);
        if (!room.getReservations().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room has reservations");
        }
        roomRepository.delete(room);
        return ResponseEntity.ok(true);
    }

    private Room getRoomById(int id) {
        return roomRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with this id does not exist"));
    }
}
