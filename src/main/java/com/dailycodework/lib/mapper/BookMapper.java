package com.dailycodework.lib.mapper;

import com.dailycodework.lib.dto.BookDto;
import com.dailycodework.lib.entity.Author;
import com.dailycodework.lib.entity.Book;
import com.dailycodework.lib.entity.Category;
import com.dailycodework.lib.entity.Publisher;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto convertToDto(Book book) {
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

    public Book convertToEntity(BookDto dto) {
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
