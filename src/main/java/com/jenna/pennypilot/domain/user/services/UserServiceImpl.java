package com.jenna.pennypilot.domain.user.services;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.category.services.CategoryService;
import com.jenna.pennypilot.domain.currency.constants.CurrencyCode;
import com.jenna.pennypilot.domain.currency.mappers.CurrencyMapper;
import com.jenna.pennypilot.domain.user.dtos.LoginDTO;
import com.jenna.pennypilot.domain.user.dtos.UserDTO;
import com.jenna.pennypilot.domain.user.mappers.UserMapper;
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
    private final CurrencyMapper currencyMapper;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isLoginInfoMatch(LoginDTO loginDTO) {
        UserDTO loginUser = userMapper.selectUserWithPasswordByEmail(loginDTO.getEmail());
        if (Objects.isNull(loginUser)) {
            throw new GlobalException(USER_EMAIL_NOT_EXISTS);
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
            this.setCurrency(user);

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
        this.validateNewUsername(user.getUsername(), oldVersion.getUsername());

        // TODO - 변경 시, 이전 내역은 지우지 말고, 같은 날 통화가 바뀌면 UI 차트 분리하는 방향으로
        this.setCurrency(user);

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
            throw new GlobalException(USER_EMAIL_NOT_EXISTS);
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

    private void validateNewUsername(String newUsername, String oldUsername) {
        if (!newUsername.equals(oldUsername)) {
            boolean isUsernameTaken = this.checkUsernameAlreadyInUse(newUsername);
            if (isUsernameTaken) throw new GlobalException(USERNAME_ALREADY_EXISTS);
        }
    }

    private void setCurrency(UserDTO user) {
        String currencyCode = user.getCurrency();

        // code가 빈값일 경우 기존 code 세팅
        if (currencyCode.isEmpty()) currencyCode = user.getCurrency();

        // 통화 정보가 없는 경우, 한국(원)을 기본값으로 설정
        if (Objects.isNull(currencyMapper.selectOneCurrencyByCode(currencyCode))) {
            currencyCode = CurrencyCode.KRW.getCode();
        }

        user.setCurrency(currencyCode);
    }
}
