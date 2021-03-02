package com.leovegas.wallet.service;

import com.leovegas.wallet.DataFactory;
import com.leovegas.wallet.dao.AccountRepository;
import com.leovegas.wallet.dao.TransactionRepository;
import com.leovegas.wallet.exception.AccountNotExistException;
import com.leovegas.wallet.exception.InvalidRequestException;
import com.leovegas.wallet.model.entity.AccountEntity;
import com.leovegas.wallet.model.entity.TransactionEntity;
import com.leovegas.wallet.model.vo.Transaction;
import com.leovegas.wallet.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void debitSuccess() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        var account = new AccountEntity(transactionDto.getAccountId(), DataFactory.HIGH_AMOUNT, null);
        when(accountRepository.findAccountByIdWithPessimisticLock(anyString())).thenReturn(Optional.of(account));
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        String expectId = transactionService.debit(transactionDto);

        // then
        assertEquals(expectId, transactionDto.getTransactionId());
        verify(transactionRepository).save(any());
        verify(accountRepository).save(any());
    }

    @Test
    void debitTransactionIdExist() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        var transaction = DataFactory.getDummyTransaction();
        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        // when
        assertThrows(InvalidRequestException.class, () -> transactionService.debit(transactionDto));

        // then
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void debitAccountNotExist() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        assertThrows(AccountNotExistException.class, () -> transactionService.debit(transactionDto));

        // then
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void creditSuccess() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        var account = new AccountEntity(transactionDto.getAccountId(), DataFactory.HIGH_AMOUNT, null);
        when(accountRepository.findAccountByIdWithPessimisticLock(anyString())).thenReturn(Optional.of(account));
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        String expectId = transactionService.credit(transactionDto);

        // then
        assertEquals(expectId, transactionDto.getTransactionId());
        verify(transactionRepository).save(any());
        verify(accountRepository).save(any());
    }

    @Test
    void creditTransactionIdExist() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        var transaction = DataFactory.getDummyTransaction();
        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        // when
        assertThrows(InvalidRequestException.class, () -> transactionService.credit(transactionDto));

        // then
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void creditAccountNotExist() {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        assertThrows(AccountNotExistException.class, () -> transactionService.credit(transactionDto));

        // then
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void getTransactionHistoryOk() {
        // given
        var historySearchDto = DataFactory.getDummyHistorySearchDto();
        List<TransactionEntity> expectTransactions = List.of(DataFactory.getDummyTransaction());
        var account = new AccountEntity(historySearchDto.getAccountId(), DataFactory.HIGH_AMOUNT, expectTransactions);
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(account));

        // when
        List<Transaction> transactions = transactionService.getTransactionHistory(historySearchDto);

        // then
        assertEquals(expectTransactions.size(), transactions.size());
        assertEquals(Transaction.toTransaction(expectTransactions.get(0)), transactions.get(0));
    }

    @Test
    void getTransactionHistoryAccountNotExist() {
        // given
        var historySearchDto = DataFactory.getDummyHistorySearchDto();
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        assertThrows(AccountNotExistException.class, () -> transactionService.getTransactionHistory(historySearchDto));

    }
}
