package com.dailycodework.librarymanagement.controller;

import com.dailycodework.librarymanagement.entity.Member;
import com.dailycodework.librarymanagement.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService memberService;

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<List<Member>> getAllActiveMembers() {
        return ResponseEntity.ok(memberService.findAllActiveMembers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/membership/{membershipNumber}")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Member> getByMembershipNumber(@PathVariable String membershipNumber) {
        return memberService.findByMembershipNumber(membershipNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Member> getByEmail(@PathVariable String email) {
        return memberService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String keyword) {
        return ResponseEntity.ok(memberService.searchMembers(keyword));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberService.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Member updatedMember = memberService.update(id, memberDetails);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/valid")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<Boolean> isValidMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.isValidMember(id));
    }

    @GetMapping("/generate-number")
    @PreAuthorize("hasAnyRole('STAFF','LIBRARIAN','ADMIN')")
    public ResponseEntity<String> generateMembershipNumber() {
        return ResponseEntity.ok(memberService.generateMembershipNumber());
    }
}
