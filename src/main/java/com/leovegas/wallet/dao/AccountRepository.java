package com.leovegas.wallet.dao;

import com.leovegas.wallet.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    @Modifying
    @Transactional
    @Override
    AccountEntity save(AccountEntity accountEntity);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.id = :id")
    Optional<AccountEntity> findAccountByIdWithPessimisticLock(String id);
}
