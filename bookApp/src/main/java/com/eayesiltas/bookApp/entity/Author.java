package com.eayesiltas.bookApp.entity;

import com.eayesiltas.bookApp.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String surname;

    @Max(value = 2015)
    private int birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nationality;
}
