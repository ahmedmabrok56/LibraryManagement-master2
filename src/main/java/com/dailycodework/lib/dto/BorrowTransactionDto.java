package com.dailycodework.lib.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BorrowTransactionDto {
    private Long id;
    private Long bookId;
    private Long memberId;
    private Long issuedByUserId;
    private Long returnedByUserId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean active;
    private BigDecimal fine;
}