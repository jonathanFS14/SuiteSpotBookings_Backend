package dat3.domain.service;

import dat3.domain.dto.HotelRequest;
import dat3.domain.dto.HotelResponse;
import dat3.domain.entity.Hotel;
import dat3.domain.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HotelServiceTest {

    @Autowired
    HotelRepository hotelRepository;
    HotelService hotelService;

    Hotel h1, h2;

    boolean isInitialized = false;
    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            h1 = new Hotel();
            h2 = new Hotel();
            hotelRepository.save(h1);
            hotelRepository.save(h2);
            hotelService = new HotelService(hotelRepository);
            isInitialized = true;
        }
    }

    @Test
    void addHotel() {
        HotelRequest h3 = new HotelRequest();
        hotelService.addHotel(h3, false);
        assertEquals(3, hotelRepository.count());
    }


    @Test
    void findById() {
        h1.setName("test");
        HotelResponse response = hotelService.findById(h1.getId(), false);
        assertEquals("test", response.getName());
    }

    @Test
    void editHotel() {
        HotelRequest h3 = new HotelRequest();
        h3.setName("test");
        hotelService.editHotel(h3, h1.getId());
        assertEquals("test", hotelRepository.findById(h1.getId()).get().getName());
    }

    @Test
    void deleteHotel() {
        hotelService.deleteHotel(h1.getId());
        assertEquals(1, hotelRepository.count());
    }
}