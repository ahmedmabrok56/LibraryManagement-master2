package com.dailycodework.lib.controller;


import com.dailycodework.lib.dto.AuthorDto;
import com.dailycodework.lib.dto.BookDto;
import com.dailycodework.lib.entity.Book;
import com.dailycodework.lib.mapper.BookMapper;
import com.dailycodework.lib.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<BookDto> dtos = bookService.findAllActiveBooks().stream().map(bookMapper::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id){
        return bookService.findById(id)
                .map(bookMapper::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn){
        return bookService.findByIsbn(isbn)
                .map(bookMapper::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }

    @GetMapping("/{search}")
    public ResponseEntity<Page<BookDto>> searchBook(@RequestParam("keyword") String keyword, Pageable pageable){
        Page<BookDto> dtos = bookService.searchBooks(keyword, pageable)
                .map(bookMapper::convertToDto);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Page<BookDto>>getBooksByAuthor(@PathVariable("id") Long authorId , Pageable pageable){
        Page<BookDto> dtos=bookService.findByAuthorId(authorId, pageable)
                .map(bookMapper::convertToDto);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<BookDto>>getBooksByCategory(@PathVariable("id") Long categoryId ){
        List<BookDto> dtos=bookService.findByCategoryId(categoryId)
                .stream()
                .map(bookMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/available")
    public ResponseEntity<List<BookDto>>getAvailableBooks(){
        List<BookDto> dtos=bookService.findAvailableBooks()
                .stream()
                .map(bookMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto){
        Book book= bookService.save(bookMapper.convertToEntity(bookDto));
    return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> updatedBook(@PathVariable("id") Long id ,@RequestBody BookDto bookDto ){
        Book book=bookMapper.convertToEntity(bookDto);
        book.setId(id);
        Book updated = bookService.update(id , book);
        return ResponseEntity.ok(bookMapper.convertToDto(updated));
    }

    @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
