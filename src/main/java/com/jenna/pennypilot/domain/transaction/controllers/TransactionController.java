package com.jenna.pennypilot.domain.transaction.controllers;

import com.jenna.pennypilot.domain.transaction.dtos.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.dtos.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;


    @Operation(summary = "사용자의 월별 전체 거래 기록", description = "userId / transactionDate 기준")
    @PostMapping("/month")
    public ResponseEntity<?> getMonthlyTransactions(@RequestBody PeriodParamDTO params) {
        return ResponseEntity.ok(transactionService.getMonthlyTransactions(params));
    }

    @Operation(summary = "지정된 기간의 전체 거래 기록", description = "userId / transactionDate / periodType(year/month/day) 기준")
    @PostMapping("/period/{periodType}")
    public ResponseEntity<?> getCtgTotalByPeriod(@PathVariable("periodType") String periodType, @RequestBody PeriodParamDTO params) {
        return ResponseEntity.ok(transactionService.getCtgTotalByPeriod(params, periodType));
    }

    @Operation(summary = "거래 기록 한개 조회", description = "id 기준")
    @GetMapping("/one/{transactionId}")
    public ResponseEntity<?> getOneTransactionsById(@PathVariable("transactionId") int transactionId) {
        return ResponseEntity.ok(transactionService.getOneTransactionById(transactionId));
    }

    @Operation(summary = "거래 기록 추가")
    @PostMapping("")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transaction) {
        return ResponseEntity.ok(transactionService.addTransaction(transaction));
    }

    @Operation(summary = "거래 기록 수정", description = "id 기준, accountId / ctgId / amount / transactionType / transactionDate / description 변경 가능")
    @PutMapping("")
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDTO transaction) {
        transactionService.updateTransaction(transaction);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "거래 기록 삭제", description = "id 기준")
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") int transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }


}
