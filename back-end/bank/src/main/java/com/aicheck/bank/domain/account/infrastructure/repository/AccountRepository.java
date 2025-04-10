package com.aicheck.bank.domain.account.infrastructure.repository;

import com.aicheck.bank.domain.account.entity.Account;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMemberId(Long memberId);

    Optional<Account> findAccountByAccountNo(String accountNo);

    List<Account> findByAccountNoIn(List<String> accountNos);

    List<String> accountNo(String accountNo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNo = :accountNo")
    Optional<Account> findWithLockByAccountNo(@Param("accountNo") String accountNo);

    Optional<Account> findByAccountNo(String accountNo);
}
