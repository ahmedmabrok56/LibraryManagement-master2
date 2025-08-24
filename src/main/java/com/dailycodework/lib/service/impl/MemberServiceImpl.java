package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.Member;
import com.dailycodework.lib.repository.MemberRepository;
import com.dailycodework.lib.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    @Override
    public List<Member> findAllActiveMembers() {
        return memberRepository.findByActiveTrue();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByMembershipNumber(String membershipNumber) {
        return memberRepository.findByMembershipNumber(membershipNumber);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Page<Member> searchMembers(String keyword, Pageable pageable) {
        return memberRepository.searchMembers(keyword,pageable);
    }

    @Override
    @Transactional
    public Member save(Member member) {
        if (member.getMembershipNumber() == null || member.getMembershipNumber().isEmpty()) {
            member.setMembershipNumber(generateMembershipNumber());
        }
        if (member.getMembershipDate() == null) {
            member.setMembershipDate(LocalDate.now());
        }
        if (member.getMembershipExpiry() == null) {
            member.setMembershipExpiry(LocalDate.now().plusYears(1));
        }
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member update(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        BeanUtils.copyProperties(memberDetails,member,"id");
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        member.setActive(false);
        memberRepository.save(member);
    }

    //year 2025 , uuid long num but i want first 6 numbers ,final LIB2025A1B2C3
    @Override
    public String generateMembershipNumber() {
        String prefix = "LIB";
        String year = String.valueOf(LocalDate.now().getYear());
        String randomPart = UUID.randomUUID().toString().substring(0,6).toUpperCase();

        return prefix+year+randomPart;
    }

    @Override
    public boolean isValidMember(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> member.isActive()&&
                        member.getMembershipExpiry().isAfter(LocalDate.now()))
                .orElse(false);
    }
}
