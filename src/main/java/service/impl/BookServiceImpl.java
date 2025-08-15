package service.impl;

import entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BookRepository;
import service.IBookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements IBookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllActiveBooks() {
        return bookRepository.findByActiveTrue();
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
        return bookRepository.searchBooks(keyword, pageable);
    }

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
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
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setEdition(bookDetails.getEdition());
        book.setLanguage(bookDetails.getLanguage());
        book.setPageCount(bookDetails.getPageCount());
        book.setSummary(bookDetails.getSummary());
        book.setCoverImageUrl(bookDetails.getCoverImageUrl());
        book.setTotalCopies(bookDetails.getTotalCopies());
        book.setPrice(bookDetails.getPrice());
        book.setAuthors(bookDetails.getAuthors());
        book.setCategory(bookDetails.getCategory());
        book.setPublisher(bookDetails.getPublisher());

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setActive(false);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void updateAvailableCopies(Long bookId, int change) {

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

            int newAvailableCount = book.getAvailableCopies() + change;
            if (newAvailableCount < 0 || newAvailableCount > book.getTotalCopies()) {
                throw new RuntimeException("Invalid available copies count");
            }

            book.setAvailableCopies(newAvailableCount);
            bookRepository.save(book);

        }

}
