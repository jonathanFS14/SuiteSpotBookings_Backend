package dat3.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.domain.entity.Reservation;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReservationRequest {

    private String username;
    private int roomId;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationDateEnd;

    public ReservationRequest(Reservation r) {
        this.username = r.getUser().getUsername();
        this.roomId = r.getRoom().getId();
        this.reservationDateStart = r.getReservationDateStart();
        this.reservationDateEnd = r.getReservationDateEnd();
    }
}
