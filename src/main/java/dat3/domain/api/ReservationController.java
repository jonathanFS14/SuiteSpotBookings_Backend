package dat3.domain.api;

import dat3.domain.dto.ReservationRequest;
import dat3.domain.dto.ReservationResponse;
import dat3.domain.dto.UserRequest;
import dat3.domain.dto.UserResponse;
import dat3.domain.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    List<ReservationResponse> getAllReservations(Principal principal) {
        return reservationService.getAllReservations(principal.getName(), false);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationResponse addReservation(@RequestBody ReservationRequest body, Principal principal){
        body.setUsername(principal.getName());
        return reservationService.makeReservation(body, false);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Boolean> deleteReservation(@PathVariable int id) {
        return reservationService.deleteReservation(id);
    }

}
