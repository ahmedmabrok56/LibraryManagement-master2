package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    private String address;
    private String city;
    private String country;
    private String website;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;
}
