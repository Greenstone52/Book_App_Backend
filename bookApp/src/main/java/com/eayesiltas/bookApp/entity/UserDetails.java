package com.eayesiltas.bookApp.entity;

import com.eayesiltas.bookApp.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String gsm;
    private String birthDate;
    private Long currentBookId = null;
}
