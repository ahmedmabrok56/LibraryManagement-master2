package com.dailycodework.lib.repository;

import com.dailycodework.lib.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByActiveTrue();

    @Query("SELECT b FROM Book b WHERE b.active = true AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Book> searchBooks(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId AND b.active = true")
    Page<Book> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.id = :categoryId AND b.active = true")
    List<Book> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT b FROM Book b JOIN b.publisher p WHERE p.id = :publisherId AND b.active = true")
    List<Book> findByPublisherId(@Param("publisherId") Long publisherId);

    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.active = true")
    List<Book> findAvailableBooks();
}
