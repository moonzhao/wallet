package com.leovegas.wallet;

import com.leovegas.wallet.model.dto.HistorySearchDto;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.model.entity.TransactionEntity;

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
        return searchDao;
    }

    public static TransactionEntity getDummyTransaction() {
        return TransactionEntity.builder()
                .id(TRANSACTION_ID)
                .accountId(ACCOUNT_ID)
                .amount(LOW_AMOUNT)
                .build();
    }
}
