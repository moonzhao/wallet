package com.leovegas.wallet.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Transactions")
public class TransactionEntity {

    @Id
    private String id;
    private String accountId;
    private String action;
    private double amount;
    private LocalDateTime transactionTime;
}
