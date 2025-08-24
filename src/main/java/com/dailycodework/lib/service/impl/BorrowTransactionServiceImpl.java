package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.Book;
import com.dailycodework.lib.entity.BorrowTransaction;
import com.dailycodework.lib.entity.Member;
import com.dailycodework.lib.entity.User;
import com.dailycodework.lib.repository.BorrowTransactionRepository;
import com.dailycodework.lib.service.IBookService;
import com.dailycodework.lib.service.IBorrowTransactionService;
import com.dailycodework.lib.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowTransactionServiceImpl implements IBorrowTransactionService {
    private final BorrowTransactionRepository transactionRepository;
    private final IBookService bookService;
    private final IMemberService memberService;

    private static final int DEFAULT_LOAN_PERIOD_DAYS = 14;
    private static final int MAX_BOOKS_PER_MEMBER = 5;
    private static final BigDecimal DAILY_FINE_RATE = new BigDecimal("0.50");

    @Override
    public List<BorrowTransaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<BorrowTransaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<BorrowTransaction> findByMemberId(Long memberId) {
        return transactionRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }

    @Override
    public List<BorrowTransaction> findActiveBorrowsByMember(Long memberId) {
        return transactionRepository.findByMemberIdAndStatus(memberId, BorrowTransaction.TransactionStatus.ACTIVE);
    }

    @Override
    public List<BorrowTransaction> findOverdueTransactions() {
        return transactionRepository.findOverdueTransactions(LocalDate.now(), BorrowTransaction.TransactionStatus.ACTIVE);
    }

    @Override
    @Transactional
    public BorrowTransaction issueBook(Long bookId, Long memberId, Long issuedByUserId) {
        //validate book
        Book book =bookService.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book not found"));

        if(book.getAvailableCopies()<=0){
            throw new RuntimeException("Book is not available for borrowing");
        }
        // Validate member
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!memberService.isValidMember(memberId)) {
            throw new RuntimeException("Member is not valid or membership has expired");
        }
        long activeBorrows = transactionRepository.countActiveBorrowsByMember(memberId, BorrowTransaction.TransactionStatus.ACTIVE);
        if (activeBorrows >= MAX_BOOKS_PER_MEMBER) {
            throw new RuntimeException("Member has reached maximum borrowing limit");
        }

        // Create transaction
        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssuedBy(new User()); // Set actual user
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(DEFAULT_LOAN_PERIOD_DAYS));
        transaction.setStatus(BorrowTransaction.TransactionStatus.ACTIVE);

        // Update book availability
        bookService.updateAvailableCopies(bookId, -1);
        return transactionRepository.save(transaction);

    }

    @Override
    @Transactional
    public BorrowTransaction returnBook(Long transactionId, Long returnedByUserId) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != BorrowTransaction.TransactionStatus.ACTIVE) {
            throw new RuntimeException("Book has already been returned");
        }

        LocalDate returnDate = LocalDate.now();
        transaction.setReturnDate(returnDate);
        transaction.setStatus(BorrowTransaction.TransactionStatus.RETURNED);
        transaction.setReturnedBy(new User()); // Set actual user

        // Calculate fine if overdue
        if (returnDate.isAfter(transaction.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), returnDate);
            BigDecimal fineAmount = DAILY_FINE_RATE.multiply(BigDecimal.valueOf(overdueDays));
            transaction.setFineAmount(fineAmount);
        }

        // Update book availability
        bookService.updateAvailableCopies(transaction.getBook().getId(), 1);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public BorrowTransaction renewBook(Long transactionId, int additionalDays) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != BorrowTransaction.TransactionStatus.ACTIVE) {
            throw new RuntimeException("Only active transactions can be renewed");
        }

        if (LocalDate.now().isAfter(transaction.getDueDate())) {
            throw new RuntimeException("Cannot renew overdue books");
        }

        transaction.setDueDate(transaction.getDueDate().plusDays(additionalDays));
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void markAsLost(Long transactionId) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(BorrowTransaction.TransactionStatus.LOST);
        // Don't update available copies as the book is lost
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void payFine(Long transactionId) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setFinePaid(true);
        transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal calculateCurrentFine(Long transactionId) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != BorrowTransaction.TransactionStatus.ACTIVE) {
            return transaction.getFineAmount();
        }

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(transaction.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), currentDate);
            return DAILY_FINE_RATE.multiply(BigDecimal.valueOf(overdueDays));
        }

        return BigDecimal.ZERO;
    }
}
