package com.dailycodework.lib.mapper;

import com.dailycodework.lib.dto.AuthorDto;
import com.dailycodework.lib.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setBirthDate(author.getBirthDate());
        dto.setDeathDate(author.getDeathDate());
        dto.setNationality(author.getNationality());
        dto.setBiography(author.getBiography());
        return dto;
    }

    public Author toEntity(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        author.setDeathDate(dto.getDeathDate());
        author.setNationality(dto.getNationality());
        author.setBiography(dto.getBiography());
        return author;
    }
}
