package com.dailycodework.librarymanagement.service;

import com.dailycodework.librarymanagement.entity.Member;

import java.util.List;
import java.util.Optional;

public interface IMemberService {
     List<Member> findAllActiveMembers();
    Optional<Member> findById(Long id);
    Optional<Member> findByMembershipNumber(String membershipNumber);
    Optional<Member> findByEmail(String email);
    List<Member> searchMembers(String keyword);
    Member save(Member member);
    Member update(Long id, Member memberDetails);
    void deleteById(Long id);

    String generateMembershipNumber();
    boolean isValidMember(Long memberId);

}
