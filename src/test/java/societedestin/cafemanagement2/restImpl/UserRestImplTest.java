import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import societedestin.cafemanagement2.dao.UserRepository;
import societedestin.cafemanagement2.dto.UserDTO;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.service.UserService;
import societedestin.cafemanagement2.restImpl.UserRestImpl;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class UserRestImplTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRestImpl userRestImpl;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "1234567890", "john@example.com", "password", "ACTIVE", "USER");
        user.setId(1L);

        userDTO = new UserDTO(1L, "John Doe", "1234567890", "john@example.com", "password","ACTIVE","USER");
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userService.registerUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserDTO> response = userRestImpl.registerUser(userDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> response = userRestImpl.registerUser(userDTO);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetUserById_Found() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userRestImpl.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(userDTO.getEmail(), result.get().getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userRestImpl.getUserById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        List<UserDTO> result = userRestImpl.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDTO.getEmail(), result.get(0).getEmail());
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);

        UserDTO result = userRestImpl.updateUser(1L, userDTO);

        assertEquals(userDTO.getEmail(), result.getEmail());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userRestImpl.deleteUser(1L));
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> userRestImpl.deleteUser(1L));
        assertEquals("User not found with id: 1", thrown.getMessage());
    }
}
