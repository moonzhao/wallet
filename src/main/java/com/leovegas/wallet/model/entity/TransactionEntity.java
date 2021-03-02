package com.leovegas.wallet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name="accountId")
    private AccountEntity account;
    private String action;
    private double amount;
    private LocalDateTime transactionTime;
}
