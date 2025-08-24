package com.dailycodework.lib.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
public class Publisher {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "publisher" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();
}
