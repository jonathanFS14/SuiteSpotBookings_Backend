package dat3.domain.api;

import dat3.domain.dto.HotelRequest;
import dat3.domain.dto.HotelResponse;
import dat3.domain.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/hotel")
public class HotelController {

    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    Page<HotelResponse> getAllHotels(Pageable pageable, @RequestParam(name = "city", required = false) String city) {
        return hotelService.getAllHotelsPagination(false, pageable, city);
    }

    /*
    @GetMapping
    List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels(false);
    }
    */

    @GetMapping("/{id}")
    HotelResponse getHotelById(@PathVariable int id) {
        return hotelService.findById(id, false);
    }


    @GetMapping("/admin/{id}")
    HotelResponse getHotelByIdAdmin(@PathVariable int id) {
        return hotelService.findById(id, true);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    HotelResponse addHotel(@RequestBody HotelRequest body) {
        return hotelService.addHotel(body, true);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Boolean> editHotel(@RequestBody HotelRequest body, @PathVariable int id) {
        return hotelService.editHotel(body, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Boolean> deleteHotel(@PathVariable int id) {
        return hotelService.deleteHotel(id);
    }

}
