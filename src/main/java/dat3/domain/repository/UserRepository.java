package dat3.domain.repository;

import dat3.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    boolean existsByEmail(String email);
}
