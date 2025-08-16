package com.dailycodework.librarymanagement.controller;

import com.dailycodework.librarymanagement.entity.BorrowTransaction;
import com.dailycodework.librarymanagement.service.IBorrowTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowTransactionController {

    private final IBorrowTransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<List<BorrowTransaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<List<BorrowTransaction>> getTransactionsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.findByMemberId(memberId));
    }

    @GetMapping("/member/{memberId}/active")
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<List<BorrowTransaction>> getActiveBorrowsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.findActiveBorrowsByMember(memberId));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<List<BorrowTransaction>> getOverdueTransactions() {
        return ResponseEntity.ok(transactionService.findOverdueTransactions());
    }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransaction> issueBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId,
            @RequestParam Long issuedByUserId) {
        return ResponseEntity.ok(transactionService.issueBook(bookId, memberId, issuedByUserId));
    }

    @PostMapping("/{transactionId}/return")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransaction> returnBook(
            @PathVariable Long transactionId,
            @RequestParam Long returnedByUserId) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId, returnedByUserId));
    }

    @PostMapping("/{transactionId}/renew")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransaction> renewBook(
            @PathVariable Long transactionId,
            @RequestParam int additionalDays) {
        return ResponseEntity.ok(transactionService.renewBook(transactionId, additionalDays));
    }

    @PostMapping("/{transactionId}/lost")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Void> markAsLost(@PathVariable Long transactionId) {
        transactionService.markAsLost(transactionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{transactionId}/pay-fine")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Void> payFine(@PathVariable Long transactionId) {
        transactionService.payFine(transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{transactionId}/fine")
    @PreAuthorize("hasRole('STAFF') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> calculateFine(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.calculateCurrentFine(transactionId));
    }
}
