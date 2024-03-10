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
public class Hotel extends AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String street;
    private String city;
    private String zip;
    private String country;
    private String phoneNumber;
    private String email;
    //private int numberOfRooms;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Room> rooms;

    public Hotel(String name, String street, String city, String zip, String country, String phoneNumber, String email) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        if (rooms == null)
            rooms = new ArrayList<>();
        rooms.add(room);
    }
}
