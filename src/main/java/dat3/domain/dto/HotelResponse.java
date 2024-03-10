package dat3.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Room;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponse {

    private int id;
    private String name;
    private String street;
    private String city;
    private String zip;
    private String country;
    private String phoneNumber;
    private String email;
    private int numberOfRooms;
    private List<RoomResponse> rooms;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime edited;

    public HotelResponse(Hotel hotel, boolean includeAll) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.street = hotel.getStreet();
        this.city = hotel.getCity();
        this.zip = hotel.getZip();
        this.country = hotel.getCountry();
        if (hotel.getRooms() == null) {
            this.numberOfRooms = 0;
        } else {
            this.numberOfRooms = hotel.getRooms().size();
        }
        if (includeAll) {
            this.created = hotel.getCreated();
            this.edited = hotel.getEdited();
            this.phoneNumber = hotel.getPhoneNumber();
            this.email = hotel.getEmail();
            this.rooms = hotel.getRooms().stream().map(r -> new RoomResponse(r, true)).toList();
        }
    }
}
