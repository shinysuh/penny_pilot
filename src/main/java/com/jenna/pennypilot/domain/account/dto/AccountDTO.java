package com.jenna.pennypilot.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private int id;

    private int userId;

    private String bankName;

    private String accountName;

    private String accountType;

    private long balance;

    private String createdAt;

    private String updatedAt;

}
