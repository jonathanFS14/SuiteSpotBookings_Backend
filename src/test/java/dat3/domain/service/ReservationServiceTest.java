package dat3.domain.service;

import dat3.domain.dto.ReservationRequest;
import dat3.domain.dto.ReservationResponse;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.Room;
import dat3.domain.entity.User;
import dat3.domain.repository.HotelRepository;
import dat3.domain.repository.ReservationRepository;
import dat3.domain.repository.RoomRepository;
import dat3.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationServiceTest {

    @Autowired
    ReservationRepository reservationRepository;
    ReservationService reservationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    Reservation r1, r2;
    Room room1, room2;
    User u1, u2;
    Hotel h1, h2;

    boolean isInitialized = false;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            h1 = new Hotel();
            h2 = new Hotel();
            hotelRepository.save(h1);
            hotelRepository.save(h2);
            room1 = roomRepository.save(new Room(1, "floor1", "1bed", "type1", "price1", "description1", h1));
            room2 = roomRepository.save(new Room(2, "floor2", "2beds", "type2", "price2", "description2", h2));
            u1 = userRepository.save(new User("user1", "pass1", "email1", "firstname1", "lastname1", "phone1", "address1", "city1", "zip1"));
            u2 = userRepository.save(new User("user2", "pass2", "email2", "firstname2", "lastname2", "phone2", "address2", "city2", "zip2"));
            r1 = reservationRepository.save(new Reservation(u1, room1, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            r2 = reservationRepository.save(new Reservation(u2, room2, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            reservationService = new ReservationService(reservationRepository, userRepository, roomRepository);
            isInitialized = true;
        }
    }

    @Test
    void findById() {
        Reservation reservation = reservationService.getReservationById(r1.getId());
        assertEquals(r1.getId(), reservation.getId());
    }

    @Test
    void findByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationService.getReservationById(3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void addReservationFailActivityIsReservedInThisPeriod() {
        ReservationRequest r3 = new ReservationRequest(u1.getUsername(), room1.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationService.makeReservation(r3, false));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void makeReservation() {
        ReservationRequest r3 = new ReservationRequest(u1.getUsername(), room1.getId(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1));
        ReservationResponse response = reservationService.makeReservation(r3,false);
        assertEquals(u1.getUsername(), response.getUser().getUsername());
        assertEquals(room1.getRoomDescription(), response.getRoom().getRoomDescription());

    }

    @Test
    void testDeleteReservationSucces() {
        reservationService.deleteReservation(r1.getId());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                reservationService.getReservationById(r1.getId()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}