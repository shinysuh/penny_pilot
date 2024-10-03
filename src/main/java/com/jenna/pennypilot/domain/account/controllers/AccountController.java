package com.jenna.pennypilot.domain.account.controllers;

import com.jenna.pennypilot.domain.account.dtos.AccountDTO;
import com.jenna.pennypilot.domain.account.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "사용자의 전체 계좌 목록 조회", description = "userId 기준")
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllAccountsByUserId(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(accountService.getAllAccountsByUserId(userId));
    }

    @Operation(summary = "계좌 상세 정보 조회", description = "하나의 계좌 정보 by id")
    @GetMapping("/detail/{accountId}")
    public ResponseEntity<?> getAccountDetailById(@PathVariable("accountId") int accountId) {
        return ResponseEntity.ok(accountService.getAccountDetailById(accountId));
    }

    @Operation(summary = "계좌 정보 추가")
    @PostMapping("")
    public ResponseEntity<?> addAccount(@RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.addAccount(account));
    }

    @Operation(summary = "계좌 정보 업데이트", description = "계좌 id를 기준으로 은행명, 계좌명, 계좌타입, 잔액(balance) 수정 가능")
    @PutMapping("")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDTO account) {
        accountService.updateAccount(account);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "계좌 삭제", description = "계좌 id 기준")
    @DeleteMapping("{accountId}")
    public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") int accountId) {
        accountService.deleteAccountById(accountId);
        return ResponseEntity.ok().build();
    }


}
