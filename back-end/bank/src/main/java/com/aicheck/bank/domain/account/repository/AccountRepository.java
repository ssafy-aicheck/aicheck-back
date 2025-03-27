package com.aicheck.bank.domain.account.repository;

import com.aicheck.bank.domain.account.entity.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMemberId(Long memberId);
}
