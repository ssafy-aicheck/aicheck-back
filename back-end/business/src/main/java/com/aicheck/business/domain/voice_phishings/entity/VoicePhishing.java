package com.aicheck.business.domain.voice_phishings.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.global.entity.BaseEntity;

@Entity
@Table(name = "voice_phishings")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class VoicePhishing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Member manager;

    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false)
    private Float score;

    @Builder
    public VoicePhishing(Member member, Member manager, String phoneNumber, Float score) {
        this.member = member;
        this.manager = manager;
        this.phoneNumber = phoneNumber;
        this.score = score;
    }
}