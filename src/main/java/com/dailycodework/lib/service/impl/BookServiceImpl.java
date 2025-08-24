package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.Book;
import com.dailycodework.lib.repository.BookRepository;
import com.dailycodework.lib.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    @Override
    public List<Book> findAllActiveBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.searchBooks(keyword,pageable);
    }

    @Override
    public Page<Book> findByAuthorId(Long authorId ,Pageable pageable) {
        return bookRepository.findByAuthorId(authorId,pageable);
    }

    @Override
    public List<Book> findByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Book> findAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(Long id, Book bookDetails) {
         Book book = bookRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        BeanUtils.copyProperties(bookDetails , book ,"id");
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Book book =bookRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Book not found with id: " + id));
        book.setActive(false);
        bookRepository.delete(book);

    }

    @Override
    @Transactional
    public void updateAvailableCopies(Long bookId, int change) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        int newAvailableCount = book.getAvailableCopies() + change;
        if (newAvailableCount < 0 || newAvailableCount > book.getAvailableCopies())
            throw new RuntimeException("Invalid available copies count");


        book.setAvailableCopies(newAvailableCount);
        bookRepository.save(book);

    }
}
