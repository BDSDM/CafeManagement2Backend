package societedestin.cafemanagement2.rest;


import societedestin.cafemanagement2.dto.UserDTO;

public interface AuthRest {
    String login(UserDTO userDTO);
}
