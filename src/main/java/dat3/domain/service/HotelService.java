package dat3.domain.service;

import dat3.domain.dto.HotelRequest;
import dat3.domain.dto.HotelResponse;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Room;
import dat3.domain.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotelService {

    HotelRepository hotelRepository;
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse addHotel(HotelRequest body, boolean includeAll) {
        Hotel hotel = HotelRequest.getHotelEntity(body);
        hotelRepository.save(hotel);
        return new HotelResponse(hotel, includeAll);
    }

    public Page<HotelResponse> getAllHotelsPagination(boolean b, Pageable pageable, String city) {
        Page<Hotel> hotels = hotelRepository.findByCityContainingIgnoreCase(city, pageable);
        return hotels.map(hotel -> new HotelResponse(hotel, b));
    }

    /*
       public List<HotelResponse> getAllHotels(boolean b) {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(((Hotel) -> new HotelResponse(Hotel, b))).toList();
    }
    */


    public HotelResponse findById(int id, boolean includeAll) {
        Hotel hotel = getHotelById(id);
        return new HotelResponse(hotel, includeAll);
    }

    public ResponseEntity<Boolean> editHotel(HotelRequest body, int id) {
        Hotel hotel = getHotelById(id);
        hotel.setName(body.getName());
        hotel.setStreet(body.getStreet());
        hotel.setCity(body.getCity());
        hotel.setZip(body.getZip());
        hotel.setCountry(body.getCountry());
        hotel.setPhoneNumber(body.getPhoneNumber());
        hotel.setEmail(body.getEmail());
        hotel.setEdited(LocalDateTime.now());
        hotelRepository.save(hotel);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> deleteHotel(int id) {
        Hotel hotel = getHotelById(id);
        for (Room room: hotel.getRooms()) {
            if (!room.getReservations().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel has rooms with reservations");
            }
        }
        hotelRepository.delete(hotel);
        return ResponseEntity.ok(true);
    }

    private Hotel getHotelById(int id) {
        return hotelRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel with this id does not exist"));
    }
}
