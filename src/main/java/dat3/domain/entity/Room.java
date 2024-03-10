package dat3.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Room extends AdminDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomNumber;
    private String floor;
    private String numberOfBeds;
    private String roomType;
    private String roomPrice;
    private String roomDescription;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    @ManyToOne
    private Hotel hotel;

    public Room(int roomNumber, String floor, String numberOfBeds, String roomType, String roomPrice, String roomDescription, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.numberOfBeds = numberOfBeds;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.reservations = new ArrayList<>();
        this.hotel = hotel;
    }

    public void addReservation(Reservation reservation){
        if (reservations == null)
            reservations = new ArrayList<>();
        reservations.add(reservation);
    }
}
