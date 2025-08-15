package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(name = "publication_year")
    private Integer publicationYear;

    private String edition;
    private String language;

    @Column(name = "page_count")
    private Integer pageCount;


    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Min(1)
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies = 1;

    @Min(0)
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies = 1;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;

    @CreatedDate
    @Column(name = "created_at" ,  updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<BorrowTransaction> borrowTransactions;

}
