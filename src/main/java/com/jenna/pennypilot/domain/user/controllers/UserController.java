package com.jenna.pennypilot.domain.user.controllers;

import com.jenna.pennypilot.domain.user.dtos.LoginDTO;
import com.jenna.pennypilot.domain.user.dtos.UserDTO;
import com.jenna.pennypilot.domain.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "email / password")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.isLoginInfoMatch(loginDTO));
    }


    // TODO - 현재 로그인 사용자 정보 조회


    @Operation(summary = "단일 사용자 정보 조회", description = "민감 정보(비밀번호) 제외")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@Parameter(description = "사용자 ID") @PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "사용자 정보 전체 조회 (관리자)", description = "username 순 정렬")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "사용자 정보 추가 (회원가입)")
    @PostMapping("")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @Operation(summary = "사용자 정보 업데이트", description = "id 필요 // username / firstName / lastName 변경 가능")
    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 업데이트", description = "필요 파라미터: id / password / oldPassword")
    @PutMapping("/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserDTO user) {
        userService.updatePassword(user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 정보 삭제 (탈퇴) - 사용자 하위 정보 일괄 삭제", description = "email / password 기준 삭제")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO user) {
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.checkEmailAlreadyInUse(email));
    }

    @Operation(summary = "Username 중복 체크")
    @GetMapping("/validate/username")
    public ResponseEntity<?> validateUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.checkUsernameAlreadyInUse(username));
    }

}
