package com.jenna.pennypilot.domain.account.services;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.account.dtos.AccountDTO;
import com.jenna.pennypilot.domain.account.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jenna.pennypilot.core.exception.ErrorCode.ACCOUNT_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;


    @Override
    public List<AccountDTO> getAllAccountsByUserId(int userId) {

        // TODO - 사용자 logged in 확인 && userId 일치 확인

        return Optional.ofNullable(accountMapper.selectAllAccountsByUserId(userId))
                .orElseGet(ArrayList::new);
    }

    @Override
    public AccountDTO getAccountDetailById(int id) {
        AccountDTO account = Optional.of(accountMapper.selectAccountById(id))
                .orElseThrow(() -> new GlobalException(ACCOUNT_NOT_EXISTS));

        // TODO - userId 가 현재 로그인 사용자 id 와 일치하는지 검증
        int userId = account.getUserId();

        return account;
    }

    @Override
    public AccountDTO addAccount(AccountDTO account) {
        accountMapper.addAccount(account);
        return account;
    }

    @Override
    public void updateAccount(AccountDTO account) {
        accountMapper.updateAccount(account);
    }

    @Override
    public void deleteAccountById(int id) {
        accountMapper.deleteAccountById(id);
    }
}
