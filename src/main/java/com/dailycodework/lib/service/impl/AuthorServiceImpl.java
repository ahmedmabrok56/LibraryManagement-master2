package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.Author;
import com.dailycodework.lib.repository.AuthorRepository;
import com.dailycodework.lib.service.IAuthorService;
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
    public Page<Author> searchAuthors(String keyword, Pageable pageable) {
        return authorRepository.searchAuthors(keyword , pageable);
    }
////////////////
    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author update(Long id, Author authorDetails) {

        Author author= authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id "+id));

        BeanUtils.copyProperties(authorDetails,author , "id");
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Author ot found By Id "+id ));
         authorRepository.delete(author);

    }
}
