package com.dailycodework.lib.repository;

import com.dailycodework.lib.entity.BorrowTransaction;
import com.dailycodework.lib.entity.BorrowTransaction.TransactionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction,Long> {
    List<BorrowTransaction> findByMemberIdAndStatus(Long memberId, BorrowTransaction.TransactionStatus status);
    List<BorrowTransaction> findByBookIdAndStatus(Long bookId , BorrowTransaction.TransactionStatus status);
    //الكتاب اتسلم بس لسا مرجعش ومتأخر
    @Query("SELECT bt FROM BorrowTransaction bt WHERE bt.dueDate < :currentDate AND bt.status = :status")
    List<BorrowTransaction> findOverdueTransactions(@Param("currentDate") LocalDate currentDate,
                                                    @Param("status") BorrowTransaction.TransactionStatus status);


    //Borrow Transaction for member id and sort desc
    @Query("SELECT bt FROM BorrowTransaction bt WHERE bt.member.id = :memberId ORDER BY bt.createdAt DESC")
    List<BorrowTransaction> findByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    //بتعد كام كتاب مستعيره العضو
    @Query("SELECT COUNT(bt) FROM BorrowTransaction bt WHERE bt.member.id = :memberId AND bt.status = :status")
    long countActiveBorrowsByMember(@Param("memberId") Long memberId, @Param("status") TransactionStatus status);
}
