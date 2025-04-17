package societedestin.cafemanagement2.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import societedestin.cafemanagement2.dao.UserRepository;
import societedestin.cafemanagement2.dao.PasswordResetTokenRepository;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.pojo.PasswordResetToken;
import societedestin.cafemanagement2.service.PasswordResetService;
import societedestin.cafemanagement2.util.EmailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailUtil emailUtil;

    private static final int EXPIRATION_MINUTES = 30;

    @Override
    @Transactional
    public void createPasswordResetToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email);
        }

        User user = optionalUser.get();

        // Supprimer l'ancien token s'il existe (écrasement)
        tokenRepository.findByUser(user).ifPresent(token -> {
            tokenRepository.delete(token);
            tokenRepository.flush(); // Force la suppression immédiate
        });

        // Générer un nouveau token
        String token = UUID.randomUUID().toString();

        // Définir la date d'expiration
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION_MINUTES);

        // Créer et sauvegarder le nouveau token
        PasswordResetToken resetToken = new PasswordResetToken(token, user, cal.getTime());
        tokenRepository.save(resetToken);

        // Construire l'URL de réinitialisation
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;

        // Préparer l'email
        String subject = "Réinitialisation de votre mot de passe";
        String body = "Bonjour,\n\nCliquez sur ce lien pour réinitialiser votre mot de passe :\n" + resetUrl + "\n\nCe lien expirera dans " + EXPIRATION_MINUTES + " minutes.";

        // Envoyer l'e-mail
        emailUtil.sendEmail(user.getEmail(), subject, body);
    }


    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isPresent()) {
            PasswordResetToken resetToken = tokenOpt.get();

            if (resetToken.getExpiryDate().before(new Date())) {
                return false;
            }

            User user = resetToken.getUser();
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);

            tokenRepository.delete(resetToken);
            return true;
        }
        return false;
    }
}
