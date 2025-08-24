package com.dailycodework.lib.controller;


import com.dailycodework.lib.dto.PublisherDto;
import com.dailycodework.lib.entity.Publisher;
import com.dailycodework.lib.mapper.PublisherMapper;
import com.dailycodework.lib.service.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final IPublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAllPublishers() {
        List<PublisherDto> dtos = publisherService.findAllPublishers()
                .stream()
                .map(publisherMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable("id") Long id) {
        return publisherService.findById(id)
                .map(publisherMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PublisherDto>> searchPublishers(@RequestParam("keyword") String keyword) {
        List<PublisherDto> dtos = publisherService.searchPublishers(keyword)
                .stream()
                .map(publisherMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherDto publisherDto) {
        Publisher publisher = publisherService.save(publisherMapper.toEntity(publisherDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherMapper.toDto(publisher));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable("id") Long id,
                                                        @RequestBody PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.toEntity(publisherDto);
        publisher.setId(id);
        Publisher updated = publisherService.update(id, publisher);
        return ResponseEntity.ok(publisherMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePublisher(@PathVariable("id") Long id) {
        publisherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
