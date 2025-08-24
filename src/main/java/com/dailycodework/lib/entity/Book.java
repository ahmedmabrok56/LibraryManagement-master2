package com.dailycodework.lib.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Column(unique = true,nullable = false)
    private String isbn;

    @Column(name = "publication_year")
    private Integer publicationYear;


    @Column(name = "edition")
    private String edition;

    @Column(name = "language")
    private String language;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "page_count")
    private Integer pageCount;

    @Min(1)
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies = 1;

    @Min(0)
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies = 1;

    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;

    @CreatedDate
    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))

    @JsonManagedReference
    private Set<Author> authors;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"books"})
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publisher_id")
    @JsonIgnoreProperties({"books"})
    private Publisher publisher;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<BorrowTransaction> borrowTransactions;


}