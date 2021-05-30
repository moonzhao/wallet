package com.leovegas.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leovegas.wallet.model.entity.AccountEntity;
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

    @JsonProperty(required = true)
    @NotBlank(message = "transactionId is mandatory")
    private String transactionId;

    @JsonProperty(required = true)
    @NotBlank(message = "accountId is mandatory")
    private String accountId;

    @JsonProperty(required = true)
    @NotNull(message = "amount is mandatory")
    @Min(value = 0L, message = "The value must be positive")
    private double amount;

    public TransactionEntity toTransactionEntity() {
        return TransactionEntity.builder()
                .id(transactionId)
                .account(new AccountEntity(accountId))
                .amount(amount)
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
