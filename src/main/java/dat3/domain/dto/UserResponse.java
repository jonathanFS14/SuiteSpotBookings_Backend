package dat3.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.domain.entity.User;
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
public class UserResponse {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String phoneNumber;
    List<ReservationResponse> reservations;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime edited;

    public UserResponse(User user, boolean includeAll) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.street = user.getStreet();
        this.city = user.getCity();
        this.zip = user.getZip();
        this.phoneNumber = user.getPhoneNumber();
        if (includeAll) {
            this.created = user.getCreated();
            this.edited = user.getEdited();
            this.reservations = user.getReservations().stream().map(c -> new ReservationResponse(c, false)).toList();
        }
    }

}
