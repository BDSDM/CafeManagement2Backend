package societedestin.cafemanagement2.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import societedestin.cafemanagement2.dao.UserRepository;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            // Vérifier si l'email existe déjà pour un autre utilisateur
            Optional<User> existingUserWithEmail = userRepository.findByEmail(updatedUser.getEmail());

            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
                // Lancer une exception avec un code d'état HTTP 400 pour une erreur de validation
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "L'email " + updatedUser.getEmail() + " est déjà utilisé par un autre utilisateur.");
            }

            // Mise à jour des champs autorisés
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setStatus(updatedUser.getStatus());
            user.setContactNumber(updatedUser.getContactNumber());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);  // Assurez-vous que la méthode findByEmail est définie dans le repository
    }
    @Override
    @Transactional
    public boolean updateUserStatus(String email, String status) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(status);  // Mise à jour du statut
            userRepository.save(user);  // Sauvegarde dans la base de données
            return true;
        }

        return false;  // L'utilisateur n'existe pas
    }
}
