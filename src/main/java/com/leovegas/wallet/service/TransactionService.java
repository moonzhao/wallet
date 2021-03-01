package com.leovegas.wallet.service;

import com.leovegas.wallet.model.dto.HistorySearchDto;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.model.vo.Transaction;

import java.util.List;

public interface TransactionService {

    String debit(TransactionDto transactionDto);

    String credit(TransactionDto transactionDto);

    List<Transaction> getTransactionHistory(HistorySearchDto historySearchDto);

}
