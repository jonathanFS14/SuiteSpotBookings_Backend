package dat3.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Reservation extends AdminDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    private User user;
    @ManyToOne()
    private Room room;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDateEnd;

    public Reservation(User user, Room room, LocalDate reservationDateStart, LocalDate reservationDateEnd) {
        this.user = user;
        this.room = room;
        this.reservationDateStart = reservationDateStart;
        this.reservationDateEnd = reservationDateEnd;
        //room.addReservation(this);
        //user.addReservation(this);
    }
}
