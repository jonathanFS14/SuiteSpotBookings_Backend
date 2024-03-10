package dat3.domain.dto;

import dat3.domain.entity.Reservation;
import dat3.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String phoneNumber;

    public static User getUserEntity(UserRequest user) {
        return new User(user.getUsername(), user.getPassword(), user.getEmail()
                , user.getFirstName(), user.getLastName(),
                user.getStreet(), user.getCity(), user.getZip(), user.getPhoneNumber());
    }

    public UserRequest(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.street = user.getStreet();
        this.city = user.getCity();
        this.zip = user.getZip();
        this.phoneNumber = user.getPhoneNumber();
    }

}
