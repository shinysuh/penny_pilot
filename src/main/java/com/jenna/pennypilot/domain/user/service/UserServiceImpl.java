package com.jenna.pennypilot.domain.user.service;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.user.dto.LoginDTO;
import com.jenna.pennypilot.domain.user.dto.UserDTO;
import com.jenna.pennypilot.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.jenna.pennypilot.core.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isLoginInfoMatch(LoginDTO loginDTO) {
        UserDTO loginUser = userMapper.selectUserWithPasswordByEmail(loginDTO.getEmail());
        if (Objects.isNull(loginUser)) {
            throw new GlobalException(USER_EMAIL_NOT_EXIST);
        }

        if (!getEncodedPassword(loginDTO.getPassword()).equals(loginUser.getPassword())) {
            throw new GlobalException(USER_PW_NOT_MATCHED);
        }

        return true;
    }

    @Override
    public UserDTO findUserById(int id) {
        return Optional.ofNullable(userMapper.selectUserById(id))
                .orElseThrow(() -> new GlobalException(USER_NOT_EXISTS));
    }

    @Override
    public List<UserDTO> findAllUsers() {
//        return Optional.of(userMapper.selectAllUsers())
//                .orElseThrow(() -> new GlobalException(EMPTY_USER_DATA));
        return Optional.ofNullable(userMapper.selectAllUsers())
                .orElseGet(ArrayList::new);
    }

    @Override
    public UserDTO addUser(UserDTO user) {
        // email validation
        boolean isEmailTaken = this.checkEmailAlreadyInUse(user.getEmail());
        if (isEmailTaken) throw new GlobalException(USER_EMAIL_ALREADY_EXISTS);

        // username validation
        boolean isUsernameTaken = this.checkUsernameAlreadyInUse(user.getUsername());
        if (isUsernameTaken) throw new GlobalException(USERNAME_ALREADY_EXISTS);

        try {
            // encode password
            user.setPassword(getEncodedPassword(user.getPassword()));
            userMapper.insertUser(user);
            // 여기 나중에 email verification 이나 가입 축하 메일 정도 보내도 될듯
            return user;
        } catch (Exception e) {
            throw new GlobalException(USER_REGISTER_ERROR, e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO user) {
        UserDTO oldVersion = this.findUserById(user.getId());

        if (Objects.isNull(oldVersion)) {
            throw new GlobalException(USER_NOT_EXISTS);
        }

        // username 변경 시, validation
        String newUsername = user.getUsername();
        if (!newUsername.equals(oldVersion.getUsername())) {
            boolean isUsernameTaken = this.checkUsernameAlreadyInUse(newUsername);
            if (isUsernameTaken) throw new GlobalException(USERNAME_ALREADY_EXISTS);
        }

        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            throw new GlobalException(USER_UPDATE_ERROR, e.getMessage());
        }
    }

    @Override
    public void updatePassword(UserDTO user) {
        try {
            // encode passwords
            user.setPassword(this.getEncodedPassword(user.getPassword()));
            user.setOldPassword(this.getEncodedPassword(user.getOldPassword()));

            userMapper.updatePassword(user);
        } catch (Exception e) {
            throw new GlobalException(USER_UPDATE_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteUser(UserDTO user) {
        UserDTO userData = userMapper.selectUserWithPasswordByEmail(user.getEmail());
        if (!this.getEncodedPassword(user.getPassword()).equals(userData.getPassword())) {
            throw new GlobalException(USER_PW_NOT_MATCHED);
        }

        try {
            userMapper.deleteUserById(userData.getId());
        } catch (Exception e) {
            throw new GlobalException(USER_DELETE_ERROR, e.getMessage());
        }
    }

    @Override
    public boolean checkEmailAlreadyInUse(String email) {
        return userMapper.checkEmailAlreadyInUse(email) > 0;
    }

    @Override
    public boolean checkUsernameAlreadyInUse(String username) {
        return userMapper.checkUsernameAlreadyInUse(username) > 0;
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
