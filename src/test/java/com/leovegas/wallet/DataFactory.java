package com.leovegas.wallet;

import com.leovegas.wallet.model.dto.HistorySearchDto;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.model.entity.AccountEntity;
import com.leovegas.wallet.model.entity.TransactionEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataFactory {

    public final static double LOW_AMOUNT = 20;
    public final static double HIGH_AMOUNT = 100;
    private final static String ACCOUNT_ID = "4";
    private final static String TRANSACTION_ID = "1";

    public static TransactionDto getDummyTransactionDto(double amount) {
        return TransactionDto.builder()
                .transactionId(TRANSACTION_ID)
                .accountId(ACCOUNT_ID)
                .amount(amount)
                .build();
    }

    public static HistorySearchDto getDummyHistorySearchDto() {
        HistorySearchDto searchDao = new HistorySearchDto();
        searchDao.setAccountId(ACCOUNT_ID);
        searchDao.setBegin(LocalDate.of(1970, 1, 1));
        searchDao.setEnd(LocalDate.now());
        return searchDao;
    }

    public static TransactionEntity getDummyTransaction() {
        return TransactionEntity.builder()
                .id(TRANSACTION_ID)
                .account(new AccountEntity(ACCOUNT_ID))
                .amount(LOW_AMOUNT)
                .transactionTime(LocalDateTime.now().minusDays(1))
                .build();
    }
}
