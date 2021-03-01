package com.leovegas.wallet.dao;

import com.leovegas.wallet.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Modifying
    @Transactional
    @Override
    TransactionEntity save(TransactionEntity transaction);

    @Query(value="select * from Transactions t where t.account_id = :accountId AND t.transaction_time >= :begin AND t.transaction_time <= :end",nativeQuery=true)
    List<TransactionEntity> findTransactionsHistory(@Param("accountId") String accountId,
                                              @Param("begin") String begin,
                                              @Param("end") String end);
}
