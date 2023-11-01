package com.eayesiltas.bookApp.entity;

import com.eayesiltas.bookApp.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "details_of_user")
public class DetailsOfUser {
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
