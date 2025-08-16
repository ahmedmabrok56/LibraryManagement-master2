package com.dailycodework.librarymanagement.service;

import com.dailycodework.librarymanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAllActiveBooks();
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    Page<Book> searchBooks(String keyword, Pageable pageable);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByCategoryId(Long categoryId);
    List<Book> findAvailableBooks();
    Book save(Book book);
    Book update(Long id, Book bookDetails);
    void deleteById(Long id);
    void updateAvailableCopies(Long bookId, int change);

    List<Book> findByPublisherId(Long publisherId);

    void deactivate(Long id);
}
