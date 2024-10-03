package com.jenna.pennypilot.domain.user.mappers;

import com.jenna.pennypilot.domain.user.dtos.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDTO selectUserById(int id);

    List<UserDTO> selectAllUsers();

    void addUser(UserDTO user);

    void updateUser(UserDTO user);

    void updatePassword(UserDTO user);

    void deleteUserById(int id);

    int checkUsernameAlreadyInUse(String username);

    int checkEmailAlreadyInUse(String email);

    UserDTO selectUserWithPasswordById(int id);

    UserDTO selectUserWithPasswordByEmail(String email);
}
