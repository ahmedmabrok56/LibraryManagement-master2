package com.dailycodework.lib.repository;

import com.dailycodework.lib.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends JpaRepository<Author , Long> {

    @Query("SELECT a FROM Author a Where "+
    "lower(a.firstName) LIKE lower(concat('%', :keyword , '%') ) OR " +
            "LOWER(a.lastName) LIKE LOWER(concat('%' ,:keyword ,'%') ) ")
    Page<Author> searchAuthors(@Param("keyword") String keyword , Pageable pageable);
}
