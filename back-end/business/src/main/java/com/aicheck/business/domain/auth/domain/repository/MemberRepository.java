package com.aicheck.business.domain.auth.domain.repository;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    List<Member> findMembersByManagerIdAndType(Long managerId, MemberType type);

    Optional<MemberType> findMemberTypeById(Long memberId);

    List<Member> findMembersByTypeAndDeletedAtIsNull(MemberType type);

    Member findMemberByAccountNo(String accountNo);

    List<Member> findMembersByManagerId(Long managerId);

    @Query("SELECT m.id FROM Member m "
        + "WHERE m.managerId = :managerId AND "
        + "m.id != :managerId AND "
        + "m.deletedAt IS NULL")
    List<Long> findMemberIdsByManagerId(@Param("managerId") Long managerId);
}
