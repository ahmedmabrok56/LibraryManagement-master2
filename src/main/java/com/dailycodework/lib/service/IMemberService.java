package com.dailycodework.lib.service;

import com.dailycodework.lib.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMemberService {
    List<Member> findAllActiveMembers();
    Optional<Member> findById(Long id);
    Optional<Member> findByMembershipNumber(String membershipNumber);
    Optional<Member> findByEmail(String email);
    Page<Member> searchMembers(String keyword, Pageable pageable);
    Member save(Member member);
    Member update(Long id, Member memberDetails);
    void deleteById(Long id);

    String generateMembershipNumber();
    boolean isValidMember(Long memberId);
}
