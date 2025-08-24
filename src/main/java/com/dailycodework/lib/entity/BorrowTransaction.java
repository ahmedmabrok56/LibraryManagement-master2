package com.dailycodework.lib.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "borrow_transactions")
@Data
@EntityListeners(AuditingEntityListener.class)
public class BorrowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //اصدر الكتاب
    @ManyToOne
    @JoinColumn(name = "issued_by", nullable = false)
    private User issuedBy;
    @ManyToOne
    @JoinColumn(name = "returned_by")
    private User returnedBy;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.ACTIVE;


    @Column(name = "fine_amount")
    private BigDecimal fineAmount = BigDecimal.ZERO;
    @Column(name = "fine_paid")
    private boolean finePaid = false;

    private String notes;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum TransactionStatus{
        // OVERDUEالكتاب اتأخر
        ACTIVE, RETURNED, OVERDUE, LOST

    }

}