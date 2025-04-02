package com.aicheck.bank.domain.account.infrastructure.repository;

import com.aicheck.bank.domain.account.entity.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMemberId(Long memberId);

    Optional<Account> findAccountByAccountNo(String accountNo);

    List<Account> findByAccountNoIn(List<String> accountNos);

    List<String> accountNo(String accountNo);
}
