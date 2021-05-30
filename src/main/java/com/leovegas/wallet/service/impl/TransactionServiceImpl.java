package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dao.AccountRepository;
import com.leovegas.wallet.dao.TransactionRepository;
import com.leovegas.wallet.exception.AccountNotExistException;
import com.leovegas.wallet.exception.ErrorMessages;
import com.leovegas.wallet.exception.InvalidRequestException;
import com.leovegas.wallet.model.dto.Action;
import com.leovegas.wallet.model.dto.HistorySearchDto;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.model.entity.AccountEntity;
import com.leovegas.wallet.model.vo.Transaction;
import com.leovegas.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public String debit(TransactionDto transactionDto) {
        var account = checkRequestData(transactionDto);
        if (account.getBalance() - transactionDto.getAmount() < 0) {
            throw new InvalidRequestException(ErrorMessages.NOT_ENOUGH_MONEY.getErrorMessage());
        }
        var transaction = transactionDto.toTransactionEntity();
        transaction.setAction(Action.DEBIT.name());
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() - transactionDto.getAmount());
        accountRepository.save(account);
        return transactionDto.getTransactionId();
    }

    @Transactional
    @Override
    public String credit(TransactionDto transactionDto) {
        var account = checkRequestData(transactionDto);
        var transaction = transactionDto.toTransactionEntity();
        transaction.setAction(Action.CREDIT.name());
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() + transactionDto.getAmount());
        accountRepository.save(account);
        return transactionDto.getTransactionId();
    }

    @Override
    public List<Transaction> getTransactionHistory(HistorySearchDto historySearchDto) {
        String accountId = historySearchDto.getAccountId();
        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotExistException(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
        }
        var transactionEntities = accountOptional.get().getTransactions();
        return transactionEntities.stream()
                .filter(t -> t.getTransactionTime().isAfter(historySearchDto.getBegin().atStartOfDay()))
                .filter(t -> t.getTransactionTime().isBefore(historySearchDto.getEnd().atStartOfDay()))
                .map(Transaction::toTransaction)
                .collect(Collectors.toList());
    }

    //@Override
    public List<Transaction> getTransactionHistory2(HistorySearchDto historySearchDto) {
        String accountId = historySearchDto.getAccountId();
        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotExistException(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
        }
        var transactionEntities = transactionRepository.findTransactionsHistory(accountId,
                historySearchDto.getBegin().toString(), historySearchDto.getEnd().toString());
        return transactionEntities.stream()
                .map(Transaction::toTransaction)
                .collect(Collectors.toList());
    }

    private AccountEntity checkRequestData(TransactionDto transactionDto) {
        // check if transactionId is unique
        var transactionOptional = transactionRepository.findById(transactionDto.getTransactionId());
        if (transactionOptional.isPresent()) {
            throw new InvalidRequestException(ErrorMessages.TRANSACTION_ID_ALREADY_EXISTS.getErrorMessage());
        }
        // check if account exist
        String accountId = transactionDto.getAccountId();
        var accountOptional = accountRepository.findAccountByIdWithPessimisticLock(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotExistException(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
        }
        return accountOptional.get();
    }
}
