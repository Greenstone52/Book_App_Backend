package com.eayesiltas.bookApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String email;
    private String password;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "userDetailsId")
    @JsonIgnore
    private UserDetails userDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_books",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    private List<Book> books;
}
