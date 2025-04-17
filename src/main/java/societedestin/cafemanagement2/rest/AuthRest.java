package societedestin.cafemanagement2.rest;

import societedestin.cafemanagement2.dto.UserDTO;
import societedestin.cafemanagement2.pojo.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthRest {
    // Modifier la signature pour renvoyer un ResponseEntity avec un AuthResponse
    ResponseEntity<AuthResponse> login(UserDTO userDTO);
}
