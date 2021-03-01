package com.leovegas.wallet.model.dto;

import com.leovegas.wallet.model.entity.TransactionEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {

    @NotBlank(message = "transactionId is mandatory")
    private String transactionId;
    @NotBlank(message = "accountId is mandatory")
    private String accountId;
    @NotNull(message = "amount is mandatory")
    @Min(value = 0L, message = "The value must be positive")
    private double amount;

    public TransactionEntity toTransactionEntity() {
        return TransactionEntity.builder()
                .id(transactionId)
                .accountId(accountId)
                .amount(amount)
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
