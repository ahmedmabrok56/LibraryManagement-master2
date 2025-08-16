package com.dailycodework.librarymanagement.service.impl;

import com.dailycodework.librarymanagement.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dailycodework.librarymanagement.repository.AuthorRepository;
import com.dailycodework.librarymanagement.service.IAuthorService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements IAuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> searchAuthors(String keyword) {
        return authorRepository.searchAuthors(keyword);
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author update(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());
        author.setBirthDate(authorDetails.getBirthDate());
        author.setDeathDate(authorDetails.getDeathDate());
        author.setNationality(authorDetails.getNationality());
        author.setBiography(authorDetails.getBiography());

        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

    @Override
    public List<Author> findAll() {
        return List.of();
    }

}
