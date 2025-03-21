package societedestin.cafemanagement2.service;

import societedestin.cafemanagement2.pojo.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean updateUserStatus(String email, String status);
    User registerUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserByEmail(String email);  // Nouvelle méthode ajoutée
}
