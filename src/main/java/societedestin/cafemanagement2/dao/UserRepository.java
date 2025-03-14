package societedestin.cafemanagement2.dao;

import org.springframework.data.repository.query.Param;
import societedestin.cafemanagement2.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
