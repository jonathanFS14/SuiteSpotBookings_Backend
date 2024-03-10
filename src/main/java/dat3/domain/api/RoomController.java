package dat3.domain.api;

import dat3.domain.dto.HotelRequest;
import dat3.domain.dto.HotelResponse;
import dat3.domain.dto.RoomRequest;
import dat3.domain.dto.RoomResponse;
import dat3.domain.service.RoomService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    RoomResponse getRoomById(@PathVariable int id) {
        return roomService.findById(id, false);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RoomResponse addRoom(@RequestBody RoomRequest body) {
        return roomService.addRoom(body, true);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Boolean> editRoom(@RequestBody RoomRequest body, @PathVariable int id) {
        return roomService.editRoom(body, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Boolean> deleteRoom(@PathVariable int id) {
        return roomService.deleteRoom(id);
    }
}
