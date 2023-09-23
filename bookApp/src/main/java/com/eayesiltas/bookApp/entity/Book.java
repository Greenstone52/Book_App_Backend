package com.eayesiltas.bookApp.entity;

import com.eayesiltas.bookApp.algorithm.ChapterAlgorithm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private int pageNumber;

    @Lob
    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    @JsonIgnore
    private List<User> users;

    @Transient
    @JsonIgnore
    private ArrayList<String> chapters = new ArrayList<>();

    public Book(Long id,String name,int pageNumber,String text,Author author,
                List<User> users){
        this.id = id;
        this.name = name;
        this.pageNumber = pageNumber;
        this.text = text;
        this.author = author;
        this.users = users;
    }
}
