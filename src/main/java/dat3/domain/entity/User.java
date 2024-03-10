package dat3.domain.entity;

import dat3.security.entity.UserWithRoles;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class User extends UserWithRoles{

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    public User(String username, String password, String email, String firstName,
                  String lastName, String street, String city, String zip, String phoneNumber) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation){
        if (reservations == null)
            reservations = new ArrayList<>();
        reservations.add(reservation);
    }
}
