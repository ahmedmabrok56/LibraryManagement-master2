package com.dailycodework.librarymanagement.controller;

import com.dailycodework.librarymanagement.dto.BookDto;
import com.dailycodework.librarymanagement.entity.Author;
import com.dailycodework.librarymanagement.entity.Book;
import com.dailycodework.librarymanagement.entity.Category;
import com.dailycodework.librarymanagement.entity.Publisher;
import com.dailycodework.librarymanagement.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> books = bookService.findAllActiveBooks();
        List<BookDto> dtos = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(convertToDto(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBooks(@RequestParam String keyword, Pageable pageable) {
        Page<Book> books = bookService.searchBooks(keyword, pageable);
        Page<BookDto> dtos = books.map(this::convertToDto);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        List<Book> books = bookService.findAvailableBooks();
        List<BookDto> dtos = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = bookService.findByAuthorId(authorId);
        List<BookDto> dtos = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable Long categoryId) {
        List<Book> books = bookService.findByCategoryId(categoryId);
        List<BookDto> dtos = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<BookDto>> getBooksByPublisher(@PathVariable Long publisherId) {
        List<Book> books = bookService.findByPublisherId(publisherId);
        List<BookDto> dtos = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(201).body(convertToDto(savedBook));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        book.setId(id);
        Book updatedBook = bookService.update(id, book);
        return ResponseEntity.ok(convertToDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateBook(@PathVariable Long id) {
        bookService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setEdition(book.getEdition());
        dto.setLanguage(book.getLanguage());
        dto.setPageCount(book.getPageCount());
        dto.setSummary(book.getSummary());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setPrice(book.getPrice());
        dto.setActive(book.isActive());
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            dto.setAuthorIds(
                    book.getAuthors().stream()
                            .map(Author::getId)
                            .collect(Collectors.toList())
            );
        }
        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getId());
        }
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getId());
        }
        return dto;
    }

    private Book convertToEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setEdition(dto.getEdition());
        book.setLanguage(dto.getLanguage());
        book.setPageCount(dto.getPageCount());
        book.setSummary(dto.getSummary());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setPrice(dto.getPrice());
        book.setActive(dto.isActive());
        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            book.setCategory(category);
        }
        if (dto.getPublisherId() != null) {
            Publisher publisher = new Publisher();
            publisher.setId(dto.getPublisherId());
            book.setPublisher(publisher);
        }
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(id -> {
                        Author a = new Author();
                        a.setId(id);
                        return a;
                    })
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        }
        return book;
    }
}
