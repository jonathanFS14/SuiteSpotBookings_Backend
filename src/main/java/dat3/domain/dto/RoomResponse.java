package dat3.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.domain.entity.Hotel;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {

    private int id;
    private int roomNumber;
    private String floor;
    private String numberOfBeds;
    private String roomType;
    private String roomPrice;
    private String roomDescription;
    private List<ReservationResponse> reservations;
    private HotelResponse hotel;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime edited;

    public RoomResponse(Room room, boolean includeAll) {
        this.roomNumber = room.getRoomNumber();
        this.floor = room.getFloor();
        this.numberOfBeds = room.getNumberOfBeds();
        this.roomType = room.getRoomType();
        this.roomPrice = room.getRoomPrice();
        this.roomDescription = room.getRoomDescription();
        if (includeAll) {
            this.id = room.getId();
            this.created = room.getCreated();
            this.edited = room.getEdited();
            this.reservations = room.getReservations().stream().map(c -> new ReservationResponse(c, false)).toList();
            this.hotel = new HotelResponse(room.getHotel(), false);
        }
    }
}
