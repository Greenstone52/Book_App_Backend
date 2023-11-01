package com.eayesiltas.bookApp.response;

import com.eayesiltas.bookApp.entity.Author;
import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class AuthorResponse {
    private String name;
    private String surname;
    private Gender gender;
    private int birthDate;
    private String nationality;

    public AuthorResponse(Author author){
        name = author.getName();
        surname = author.getSurname();
        gender = author.getGender();
        birthDate = author.getBirthDate();
        nationality = author.getNationality();
    }
}
