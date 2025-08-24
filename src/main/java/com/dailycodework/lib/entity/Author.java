package com.dailycodework.lib.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"first_name","last_name"})
})
@Data
public class Author {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "first_name" , nullable = false,length = 100)
    @NotBlank(message="first name is required")
    @Size(max=100 , message = "first name cannot exceed 100 characters")
    private String firstName;

    @Column(name = "last_name" , nullable = false,length = 100)
    @NotBlank(message="last name is required")
    @Size(max=100 , message = "last name cannot exceed 100 characters")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @Size(max = 100 , message= "Nationality cannot exceed 100 characters")
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @ManyToMany(mappedBy = "authors" ,cascade = {CascadeType.PERSIST , CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Book> books;


}
