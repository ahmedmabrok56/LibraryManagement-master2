package com.dailycodework.librarymanagement.service;
import com.dailycodework.librarymanagement.entity.BorrowTransaction;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public interface IBorrowTransactionService {
    List<BorrowTransaction> findAllTransactions();
    Optional<BorrowTransaction> findById(Long id);
    List<BorrowTransaction> findByMemberId(Long memberId);
    List<BorrowTransaction> findActiveBorrowsByMember(Long memberId);
    List<BorrowTransaction> findOverdueTransactions();
    BorrowTransaction issueBook(Long bookId, Long memberId, Long issuedByUserId);

    BorrowTransaction returnBook(Long transactionId, Long returnedByUserId);
    BorrowTransaction renewBook(Long transactionId, int additionalDays);
    void markAsLost(Long transactionId);
    void payFine(Long transactionId);
    BigDecimal calculateCurrentFine(Long transactionId);

}
