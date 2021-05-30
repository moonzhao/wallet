package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dao.AccountRepository;
import com.leovegas.wallet.exception.AccountNotExistException;
import com.leovegas.wallet.exception.ErrorMessages;
import com.leovegas.wallet.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public double getAccountBalance(String accountId) {
        var account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            log.error(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
            throw new AccountNotExistException(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
        }
        return account.get().getBalance();
    }
}
