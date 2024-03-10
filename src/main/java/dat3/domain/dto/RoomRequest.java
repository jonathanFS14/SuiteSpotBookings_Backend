package dat3.domain.dto;

import dat3.domain.entity.Room;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomRequest {

    private int roomNumber;
    private String floor;
    private String numberOfBeds;
    private String roomType;
    private String roomPrice;
    private String roomDescription;
    private int hotelId;

    public RoomRequest(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.floor = room.getFloor();
        this.numberOfBeds = room.getNumberOfBeds();
        this.roomType = room.getRoomType();
        this.roomPrice = room.getRoomPrice();
        this.roomDescription = room.getRoomDescription();
        this.hotelId = room.getHotel().getId();
    }
}
