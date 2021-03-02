package com.leovegas.wallet.service;

import com.leovegas.wallet.dao.AccountRepository;
import com.leovegas.wallet.exception.AccountNotExistException;
import com.leovegas.wallet.model.entity.AccountEntity;
import com.leovegas.wallet.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountBalance_success() {
        // given
        String accountId = "2";
        double expectBalance = 300;
        AccountEntity accountEntity = new AccountEntity(accountId, expectBalance, null);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        // when
        double balance = accountService.getAccountBalance(accountId);

        // then
        assertEquals(expectBalance, balance);
    }

    @Test
    void getAccountBalance_accountNotExist() {
        // given
        String accountId = "2";
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // when
        assertThrows(AccountNotExistException.class, () -> accountService.getAccountBalance(accountId));
    }
}
