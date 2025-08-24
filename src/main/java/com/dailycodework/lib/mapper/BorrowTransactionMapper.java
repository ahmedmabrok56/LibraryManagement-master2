package com.dailycodework.lib.mapper;

import com.dailycodework.lib.dto.BorrowTransactionDto;
import com.dailycodework.lib.entity.BorrowTransaction;
import org.springframework.stereotype.Component;

@Component
public class BorrowTransactionMapper {

    public BorrowTransactionDto toDto(BorrowTransaction transaction) {
        BorrowTransactionDto dto = new BorrowTransactionDto();
        dto.setId(transaction.getId());
        dto.setBookId(transaction.getBook().getId());
        dto.setMemberId(transaction.getMember().getId());
        if (transaction.getIssuedBy() != null) {
            dto.setIssuedByUserId(transaction.getIssuedBy().getId());
        }
        if (transaction.getReturnedBy() != null) {
            dto.setReturnedByUserId(transaction.getReturnedBy().getId());
        }
        dto.setIssueDate(transaction.getIssueDate());
        dto.setDueDate(transaction.getDueDate());
        dto.setReturnDate(transaction.getReturnDate());

        return dto;
    }

    public BorrowTransaction toEntity(BorrowTransactionDto dto) {
        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setId(dto.getId());
        transaction.setIssueDate(dto.getIssueDate());
        transaction.setDueDate(dto.getDueDate());
        transaction.setReturnDate(dto.getReturnDate());
        return transaction;
    }
}
