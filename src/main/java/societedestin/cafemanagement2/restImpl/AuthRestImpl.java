package societedestin.cafemanagement2.restImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import societedestin.cafemanagement2.dto.UserDTO;
import societedestin.cafemanagement2.jwt.JwtUtil;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.rest.AuthRest;
import societedestin.cafemanagement2.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthRestImpl implements AuthRest {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;  // Injection de PasswordEncoder

    public AuthRestImpl(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;  // Injection de PasswordEncoder
    }

    @PostMapping("/login")
    @Override
    public String login(@RequestBody UserDTO userDTO) {
        User user = userService.getUserByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification du mot de passe avec BCryptPasswordEncoder
        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        } else {
            throw new RuntimeException("Mot de passe incorrect");
        }
    }
}
