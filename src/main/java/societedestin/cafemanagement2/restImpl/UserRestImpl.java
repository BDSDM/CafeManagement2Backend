package societedestin.cafemanagement2.restImpl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import societedestin.cafemanagement2.dao.UserRepository;
import societedestin.cafemanagement2.dto.UserDTO;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.rest.UserRest;
import societedestin.cafemanagement2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserRestImpl implements UserRest {

    private final UserService userService;
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserRestImpl(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping("/register")
    @Override
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        // Vérifier si un utilisateur avec le même email existe déjà
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        // Si l'utilisateur existe déjà, retourner une réponse avec un message d'erreur (HTTP 400 Bad Request)
        if (existingUser.isPresent()) {
            // Vous pouvez retourner un message d'erreur détaillé pour informer que l'email est déjà pris
            return ResponseEntity.badRequest().body(null);  // Ou personnaliser le message d'erreur ici
        }

        // Créer un objet User à partir de UserDTO
        User user = new User();
        user.setName(userDTO.getName());
        user.setContactNumber(userDTO.getContactNumber());
        user.setEmail(userDTO.getEmail());

        // Encoder le mot de passe avant de le sauvegarder
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Enregistrer l'utilisateur avec le mot de passe encodé
        User savedUser = userService.registerUser(user);

        // Retourner un UserDTO sans le mot de passe (pour des raisons de sécurité)
        UserDTO responseUserDTO = new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getContactNumber(), savedUser.getEmail(), null);

        // Retourner la réponse avec le UserDTO dans un ResponseEntity (200 OK)
        return ResponseEntity.ok(responseUserDTO);
    }


    @GetMapping("/{id}")
    @Override
    public Optional<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        return userService.getUserById(id)
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getContactNumber(),user.getEmail(), user.getPassword()));
    }

    @GetMapping
    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(),user.getName(),user.getContactNumber(), user.getEmail(), user.getPassword()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Override
    public UserDTO updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = new User();
        updatedUser.setName(userDTO.getName());
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userService.updateUser(id, updatedUser);
        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getContactNumber(), savedUser.getEmail(), savedUser.getPassword());
    }


    @DeleteMapping("/{id}")
    @Override
    public void deleteUser(@PathVariable(name = "id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }
}
