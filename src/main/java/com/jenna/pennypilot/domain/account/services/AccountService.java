package com.jenna.pennypilot.domain.account.services;

import com.jenna.pennypilot.domain.account.dtos.AccountDTO;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAllAccountsByUserId(int userId);

    AccountDTO getAccountDetailById(int id);

    AccountDTO addAccount(AccountDTO account);

    void updateAccount(AccountDTO account);

    void deleteAccountById(int id);
}
