package societedestin.cafemanagement2.dao;

import societedestin.cafemanagement2.pojo.PasswordResetToken;
import societedestin.cafemanagement2.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
    void deleteByUser(User user);

}
