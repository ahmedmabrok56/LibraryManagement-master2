package com.dailycodework.librarymanagement.repository;

import com.dailycodework.librarymanagement.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member>findByMembershipNumber(String mempershipNumber);
    Optional<Member>findByEmail(String email);

    List<Member> findByActiveTrue();

    @Query("select m from Member m where m.active = true AND "+
    "(lower(m.firstName) like lower(concat('%' ,:keyword , '%') )OR "+
    "lower(m.lastName) like lower(concat('%' , :keyword , '%') )OR "+
     "LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
      "m.membershipNumber LIKE CONCAT('%', :keyword, '%'))")
    List<Member> searchMembers(@Param("keyword") String keyword);


}
