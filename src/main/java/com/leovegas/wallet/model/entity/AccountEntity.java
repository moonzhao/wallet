package com.leovegas.wallet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class AccountEntity {

    @Id
    private String id;
    private double balance;

    @OneToMany(mappedBy = "account",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<TransactionEntity> transactions;

    public AccountEntity(String id) {
        this.id = id;
    }
}
