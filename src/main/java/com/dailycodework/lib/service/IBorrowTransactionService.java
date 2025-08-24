package com.dailycodework.lib.service;
import com.dailycodework.lib.entity.BorrowTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IBorrowTransactionService {
    List<BorrowTransaction> findAllTransactions();
    Optional<BorrowTransaction> findById(Long id);
    List<BorrowTransaction> findByMemberId(Long memberId);
    List<BorrowTransaction> findActiveBorrowsByMember(Long memberId);
    List<BorrowTransaction> findOverdueTransactions();
    BorrowTransaction issueBook(Long bookId , Long memberId , Long issuedByUserId);
    BorrowTransaction returnBook(Long transactionId , Long returnedByUserId);
    BorrowTransaction renewBook(Long transactionId , int additionalDays);
    void markAsLost(Long transactionId);
    void payFine(Long transactionId);
    BigDecimal calculateCurrentFine(Long transactionId);


}
