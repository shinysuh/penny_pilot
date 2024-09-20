package com.jenna.pennypilot.domain.user.service;

import com.jenna.pennypilot.domain.user.dto.LoginDTO;
import com.jenna.pennypilot.domain.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    boolean isLoginInfoMatch(LoginDTO loginDTO);

    UserDTO getUserById(int id);

    List<UserDTO> getAllUsers();

    UserDTO addUser(UserDTO user);

    void updateUser(UserDTO user);

    void updatePassword(UserDTO user);

    void deleteUser(UserDTO user);

    boolean checkEmailAlreadyInUse(String email);

    boolean checkUsernameAlreadyInUse(String username);

}
