package com.dailycodework.lib.controller;

import com.dailycodework.lib.dto.MemberDto;
import com.dailycodework.lib.entity.Member;
import com.dailycodework.lib.mapper.MemberMapper;
import com.dailycodework.lib.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberDto> dtos = memberService.findAllActiveMembers()
                .stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable("id") Long id) {
        return memberService.findById(id)
                .map(memberMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/membership/{membershipNumber}")
    public ResponseEntity<MemberDto> getMemberByMembershipNumber(@PathVariable("membershipNumber") String membershipNumber) {
        return memberService.findByMembershipNumber(membershipNumber)
                .map(memberMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberDto> getMemberByEmail(@PathVariable("email") String email) {
        return memberService.findByEmail(email)
                .map(memberMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MemberDto>> searchMembers(@RequestParam("keyword") String keyword, Pageable pageable) {
        Page<MemberDto> dtos = memberService.searchMembers(keyword, pageable)
                .map(memberMapper::toDto);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        Member member = memberService.save(memberMapper.toEntity(memberDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(memberMapper.toDto(member));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        Member member = memberMapper.toEntity(memberDto);
        member.setId(id);
        Member updated = memberService.update(id, member);
        return ResponseEntity.ok(memberMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generate-membership-number")
    public ResponseEntity<String> generateMembershipNumber() {
        String membershipNumber = memberService.generateMembershipNumber();
        return ResponseEntity.ok(membershipNumber);
    }

    @GetMapping("/{id}/is-valid")
    public ResponseEntity<Boolean> isValidMember(@PathVariable("id") Long memberId) {
        boolean valid = memberService.isValidMember(memberId);
        return ResponseEntity.ok(valid);
    }
}
