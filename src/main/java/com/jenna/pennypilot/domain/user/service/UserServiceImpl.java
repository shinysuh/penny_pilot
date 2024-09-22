package com.jenna.pennypilot.domain.user.service;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.category.service.CategoryService;
import com.jenna.pennypilot.domain.user.dto.LoginDTO;
import com.jenna.pennypilot.domain.user.dto.UserDTO;
import com.jenna.pennypilot.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final CategoryService categoryService;

    @Override
    public boolean isLoginInfoMatch(LoginDTO loginDTO) {
        UserDTO loginUser = userMapper.selectUserWithPasswordByEmail(loginDTO.getEmail());
        if (Objects.isNull(loginUser)) {
            throw new GlobalException(USER_EMAIL_NOT_EXIST);
        }

        this.checkPassword(loginDTO.getPassword(), loginUser.getPassword());

        return true;
    }

    @Override
    public UserDTO getUserById(int id) {
        return Optional.ofNullable(userMapper.selectUserById(id))
                .orElseThrow(() -> new GlobalException(USER_NOT_EXISTS));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return Optional.ofNullable(userMapper.selectAllUsers())
                .orElseGet(ArrayList::new);
    }

    @Transactional(rollbackFor = Exception.class)
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
            user.setPassword(this.getEncodedPassword(user.getPassword()));

            user.setFirstName(user.getFirstName().toLowerCase());
            user.setLastName(user.getLastName().toLowerCase());

            // 사용자 정보 추가
            userMapper.addUser(user);

            // 기본 카테고리 정보 추가
            categoryService.addBasicCategories(user);

            // 여기 나중에 email verification 이나 가입 축하 메일 정도 보내도 될듯

            return user;
        } catch (Exception e) {
            throw new GlobalException(USER_REGISTER_ERROR, e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO user) {
        UserDTO oldVersion = this.getUserById(user.getId());

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
        UserDTO userData = userMapper.selectUserWithPasswordById(user.getId());
        this.checkPassword(user.getOldPassword(), userData.getPassword());

        try {

            // encode passwords
            user.setPassword(this.getEncodedPassword(user.getPassword()));
            user.setOldPassword(userData.getPassword());    // 이전에 encoded 된 비밀번호

            userMapper.updatePassword(user);
        } catch (Exception e) {
            throw new GlobalException(USER_UPDATE_ERROR, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(UserDTO user) {
        UserDTO userData = userMapper.selectUserWithPasswordByEmail(user.getEmail());

        if (Objects.isNull(userData)) {
            throw new GlobalException(USER_EMAIL_NOT_EXIST);
        }

        this.checkPassword(user.getPassword(), userData.getPassword());

        try {
            userMapper.deleteUserById(userData.getId());

            /* TODO (개발 후반) - 사용자 하위 계좌, 카테고리, 버짓, 트랜잭션 일괄 삭제 기능 추가
             *       >> 프론트에서 confirm 필요
             * */

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

    private void checkPassword(String raw, String encoded) {
        if (!passwordEncoder.matches(raw, encoded)) {
            throw new GlobalException(USER_PW_NOT_MATCHED);
        }
    }
}
