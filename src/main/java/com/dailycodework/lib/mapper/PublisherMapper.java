package com.dailycodework.lib.mapper;


import com.dailycodework.lib.dto.PublisherDto;
import com.dailycodework.lib.entity.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    // تحويل من Entity إلى DTO
    public PublisherDto toDto(Publisher publisher) {
        if (publisher == null) return null;

        PublisherDto dto = new PublisherDto();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setCity(publisher.getCity());
        dto.setCountry(publisher.getCountry());
        dto.setWebsite(publisher.getWebsite());
        return dto;
    }

    // تحويل من DTO إلى Entity
    public Publisher toEntity(PublisherDto dto) {
        if (dto == null) return null;

        Publisher publisher = new Publisher();
        publisher.setId(dto.getId());
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setCity(dto.getCity());
        publisher.setCountry(dto.getCountry());
        publisher.setWebsite(dto.getWebsite());
        return publisher;
    }
}