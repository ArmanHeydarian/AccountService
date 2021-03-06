package com.banking.accounts.domain.account.service;

import com.banking.accounts.domain.account.dto.AccountDto;
import com.banking.accounts.domain.account.entity.Account;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface IAccountFactory {
    Account createAccount(AccountDto accountDto);
}
