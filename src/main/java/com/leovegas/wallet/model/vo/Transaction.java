package com.leovegas.wallet.model.vo;

import com.leovegas.wallet.model.entity.TransactionEntity;
import com.leovegas.wallet.utils.WalletUtils;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    private String accountId;
    private String action;
    private double amount;
    private LocalDateTime transactionTime;

    public static Transaction toTransaction(TransactionEntity entity) {
        return Transaction.builder()
                .accountId(entity.getAccount().getId())
                .action(entity.getAction())
                .amount(entity.getAmount())
                .transactionTime(WalletUtils.formatDateTime(entity.getTransactionTime()))
                .build();
    }
}
