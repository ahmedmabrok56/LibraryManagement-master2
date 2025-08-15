package service.impl;

import entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MemberRepository;
import service.IMemberService;

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
    public List<Member> searchMembers(String keyword) {
        return memberRepository.searchMembers(keyword);
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

        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setAddress(memberDetails.getAddress());
        member.setDateOfBirth(memberDetails.getDateOfBirth());
        member.setMembershipExpiry(memberDetails.getMembershipExpiry());

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

    @Override
    public String generateMembershipNumber() {
        String prefix = "LIB";
        String year = String.valueOf(LocalDate.now().getYear());
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return prefix + year + randomPart;
    }

    @Override
    public boolean isValidMember(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> member.isActive() &&
                        member.getMembershipExpiry().isAfter(LocalDate.now()))
                .orElse(false);
    }
}
