package com.dailycodework.lib.controller;

import com.dailycodework.lib.dto.BorrowTransactionDto;
import com.dailycodework.lib.entity.BorrowTransaction;
import com.dailycodework.lib.mapper.BorrowTransactionMapper;
import com.dailycodework.lib.service.IBorrowTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowTransactionController {

    private final IBorrowTransactionService transactionService;
    private final BorrowTransactionMapper mapper;

    @GetMapping
    public ResponseEntity<List<BorrowTransactionDto>> getAllTransactions() {
        List<BorrowTransactionDto> dtos = transactionService.findAllTransactions()
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowTransactionDto> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowTransactionDto>> getTransactionsByMember(@PathVariable Long memberId) {
        List<BorrowTransactionDto> dtos = transactionService.findByMemberId(memberId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<List<BorrowTransactionDto>> getActiveBorrows(@PathVariable Long memberId) {
        List<BorrowTransactionDto> dtos = transactionService.findActiveBorrowsByMember(memberId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowTransactionDto>> getOverdueTransactions() {
        List<BorrowTransactionDto> dtos = transactionService.findOverdueTransactions()
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransactionDto> issueBook(@RequestParam Long bookId,
                                                          @RequestParam Long memberId,
                                                          @RequestParam Long issuedByUserId) {
        BorrowTransaction transaction = transactionService.issueBook(bookId, memberId, issuedByUserId);
        return ResponseEntity.ok(mapper.toDto(transaction));
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransactionDto> returnBook(@PathVariable Long id,
                                                           @RequestParam Long returnedByUserId) {
        BorrowTransaction transaction = transactionService.returnBook(id, returnedByUserId);
        return ResponseEntity.ok(mapper.toDto(transaction));
    }

    @PostMapping("/{id}/renew")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BorrowTransactionDto> renewBook(@PathVariable Long id,
                                                          @RequestParam int additionalDays) {
        BorrowTransaction transaction = transactionService.renewBook(id, additionalDays);
        return ResponseEntity.ok(mapper.toDto(transaction));
    }

    @PostMapping("/{id}/lost")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Void> markAsLost(@PathVariable Long id) {
        transactionService.markAsLost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay-fine")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Void> payFine(@PathVariable Long id) {
        transactionService.payFine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/fine")
    public ResponseEntity<BigDecimal> calculateFine(@PathVariable Long id) {
        BigDecimal fine = transactionService.calculateCurrentFine(id);
        return ResponseEntity.ok(fine);
    }
}
