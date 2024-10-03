package com.jenna.pennypilot.domain.account.mappers;

import com.jenna.pennypilot.domain.account.dtos.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {

    List<AccountDTO> selectAllAccountsByUserId(int userId);

    AccountDTO selectAccountById(int id);

    void addAccount(AccountDTO account);

    void updateAccount(AccountDTO account);

    void deleteAccountById(int id);
}
