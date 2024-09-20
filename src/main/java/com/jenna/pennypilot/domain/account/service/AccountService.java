package com.jenna.pennypilot.domain.account.service;

import com.jenna.pennypilot.domain.account.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAllAccountsByUserId(int userId);

    AccountDTO getAccountDetailById(int id);

    AccountDTO addAccount(AccountDTO account);

    void updateAccount(AccountDTO account);

    void deleteAccountById(int id);
}
