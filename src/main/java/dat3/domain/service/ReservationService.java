package dat3.domain.service;

import dat3.domain.dto.ReservationRequest;
import dat3.domain.dto.ReservationResponse;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.Room;
import dat3.domain.entity.User;
import dat3.domain.repository.ReservationRepository;
import dat3.domain.repository.RoomRepository;
import dat3.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    UserRepository userRepository;
    RoomRepository roomRepository;
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public ReservationResponse makeReservation(ReservationRequest body, boolean b) {
        if(body.getReservationDateStart().isBefore(LocalDate.now()) || body.getReservationDateEnd().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }
        if (body.getReservationDateStart().isAfter(body.getReservationDateEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be before end date");
        }
        if(body.getReservationDateStart().isEqual(body.getReservationDateEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date and end date cannot be the same");
        }
        User user = userRepository.findById(body.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with this id found"));
        Room room = roomRepository.findById(body.getRoomId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with this id found"));
        List<Reservation> roomReservation = room.getReservations();
        if (!roomReservation.isEmpty()) {
            for (Reservation r : roomReservation) {
                if (body.getReservationDateStart().isBefore(r.getReservationDateEnd()) &&
                        body.getReservationDateEnd().isAfter(r.getReservationDateStart()) ||
                        body.getReservationDateStart().isEqual(r.getReservationDateStart()) ||
                        body.getReservationDateEnd().isEqual(r.getReservationDateEnd())
                ) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is reserved in this period");
                }
            }
        }
        Reservation reservation = reservationRepository.save(new Reservation(user, room, body.getReservationDateStart(), body.getReservationDateEnd()));
        return new ReservationResponse(reservation, false);
    }

    public ResponseEntity<Boolean> deleteReservation(int id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
        return ResponseEntity.ok(true);
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with this id does not exist"));
    }

    public List<ReservationResponse> getAllReservations(String name, boolean b) {
        User user = userRepository.findById(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with this id found"));
        List<Reservation> reservations = reservationRepository.findAllByUser(user);
        return ReservationResponse.getReservationResponseList(reservations, false);
    }
}
