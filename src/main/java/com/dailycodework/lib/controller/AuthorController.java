package com.dailycodework.lib.controller;

import com.dailycodework.lib.dto.AuthorDto;
import com.dailycodework.lib.entity.Author;
import com.dailycodework.lib.mapper.AuthorMapper;
import com.dailycodework.lib.service.IAuthorService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final IAuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        List<AuthorDto> dtos = authorService.findAllAuthors().stream().map(authorMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id){
    return authorService.findById(id)
            .map(authorMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() ->ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity<Page<AuthorDto>> searchAuthor(@RequestParam("keyword") String keyword , Pageable pageable){
    Page<AuthorDto> dtos = authorService.searchAuthors(keyword,pageable)
            .map(authorMapper::toDto);
    return ResponseEntity.ok(dtos);
    }

    @PostMapping
 //   @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorService.save(authorMapper.toEntity(authorDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.toDto(author));
    }

    @PutMapping("/{id}")
    //   @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> updatedAuthor(@PathVariable("id") Long id , @RequestBody AuthorDto authorDto){
        Author author =authorMapper.toEntity(authorDto);
        author.setId(id);
        Author updated = authorService.update(id ,author);
        return ResponseEntity.ok(authorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
