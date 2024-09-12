package com.jenna.pennypilot.domain.user.controller;

import com.jenna.pennypilot.domain.user.dto.LoginDTO;
import com.jenna.pennypilot.domain.user.dto.UserDTO;
import com.jenna.pennypilot.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "email / password")
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.isLoginInfoMatch(loginDTO));
    }


    // TODO - 로그인 사용자 정보 조회


    @ApiOperation(value = "단일 사용자 정보 조회", notes = "민감 정보(비밀번호) 제외")
    @GetMapping("")
    public ResponseEntity<?> findUserById(@ApiParam(value = "사용자 ID") @RequestParam int id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @ApiOperation(value = "사용자 정보 전체 조회 (관리자)", notes = "username 순 정렬")
    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @ApiOperation(value = "사용자 정보 추가 (회원가입)")
    @PostMapping("")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 정보 업데이트", notes = "username / firstName / lastName 변경 가능")
    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "비밀번호 업데이트", notes = "필요 파라미터: id / password / oldPassword")
    @PutMapping("/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserDTO user) {
        userService.updatePassword(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 정보 삭제 (탈퇴)", notes = "email / password 기준 삭제")
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO user) {
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "이메일 중복 체크")
    @GetMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.checkEmailAlreadyInUse(email));
    }

    @ApiOperation(value = "Username 중복 체크")
    @GetMapping("/validate/username")
    public ResponseEntity<?> validateUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.checkUsernameAlreadyInUse(username));
    }

}
