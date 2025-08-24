package com.dailycodework.lib.service;

import com.dailycodework.lib.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAllActiveBooks();
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    Page<Book> searchBooks(String keyword , Pageable pageable);
    Page<Book> findByAuthorId(Long authorId ,Pageable pageable);
    List<Book> findByCategoryId(Long categoryId);
    List<Book> findAvailableBooks();
    Book save(Book book);
    Book update(Long id , Book bookDetails);
    void deleteById(Long id);
    void updateAvailableCopies(Long bookId , int change);


}
