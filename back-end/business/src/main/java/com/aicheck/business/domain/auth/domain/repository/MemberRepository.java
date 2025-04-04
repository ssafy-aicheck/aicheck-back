package com.aicheck.business.domain.auth.domain.repository;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    List<Member> findMembersByManagerIdAndType(Long managerId, MemberType type);

    Optional<MemberType> findMemberTypeById(Long memberId);

    List<Member> findMembersByTypeAndDeletedAtIsNull(MemberType type);
}
