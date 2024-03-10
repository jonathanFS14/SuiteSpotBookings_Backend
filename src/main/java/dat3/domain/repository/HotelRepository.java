package dat3.domain.repository;

import dat3.domain.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("SELECT h FROM Hotel h WHERE " +
            "(LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%')) OR :city IS NULL)")
    Page<Hotel> findByCityContainingIgnoreCase(@Param("city") String city, Pageable pageable);
}
