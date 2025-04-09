package com.aicheck.bank.domain.account.entity;

import com.aicheck.bank.domain.member.entity.Member;
import com.aicheck.bank.global.exception.BankErrorCodes;
import com.aicheck.bank.global.exception.BankException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_no", nullable = false, unique = true, length = 20)
    private String accountNo;

    @Column(name = "password", nullable = false, length = 4)
    private String password;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void deposit(Long amount) {
        balance += amount;
    }

    public void withdraw(Long amount) {
        if (balance < amount) {
            throw new BankException(BankErrorCodes.INSUFFICIENT_BALANCE);
        }
        balance -= amount;
    }

}