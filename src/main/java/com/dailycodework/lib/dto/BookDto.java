package com.dailycodework.lib.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String edition;
    private String language;
    private Integer pageCount;
    private String summary;
    private String coverImageUrl;
    private Integer totalCopies;
    private Integer availableCopies;
    private BigDecimal price;
    private boolean active;
    private List<Long> authorIds;
    private Long categoryId;
    private Long publisherId;
}
