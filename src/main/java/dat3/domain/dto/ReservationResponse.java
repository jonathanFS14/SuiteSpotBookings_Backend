package dat3.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.Room;
import dat3.domain.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
public class ReservationResponse {

    private int id;
    private UserResponse user;
    private RoomResponse room;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationDateEnd;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime edited;

    public ReservationResponse(Reservation reservation, boolean includeAll) {
        this.id = reservation.getId();
        this.user = new UserResponse(reservation.getUser(), false);
        this.room = new RoomResponse(reservation.getRoom(), false);
        this.reservationDateStart = reservation.getReservationDateStart();
        this.reservationDateEnd = reservation.getReservationDateEnd();
        if (includeAll) {
            this.id = reservation.getId();
            this.created = reservation.getCreated();
            this.edited = reservation.getEdited();
        }
    }

    public static List<ReservationResponse> getReservationResponseList(List<Reservation> reservations, boolean b) {
        return reservations.stream().map(r -> new ReservationResponse(r, b)).toList();
    }
}

