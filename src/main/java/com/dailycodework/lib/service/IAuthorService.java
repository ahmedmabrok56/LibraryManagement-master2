package com.dailycodework.lib.service;

import com.dailycodework.lib.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    List<Author> findAllAuthors();
    Optional<Author> findById(Long id);
    Page<Author> searchAuthors(String keyword , Pageable pageable);
    Author save(Author author);
    Author update(Long id , Author authorDetails);
    void deleteById(Long id);

}
