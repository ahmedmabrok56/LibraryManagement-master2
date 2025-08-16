package com.dailycodework.librarymanagement.service;

import com.dailycodework.librarymanagement.entity.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    List<Author> findAllAuthors();
    Optional<Author> findById(Long id);
    List<Author> searchAuthors(String keyword);
    Author save(Author author);
    Author update(Long id, Author authorDetails);
    void deleteById(Long id);

    List<Author> findAll();

}
