package dat3.domain.repository;

import dat3.domain.entity.Reservation;
import dat3.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByUser(User user);

}
