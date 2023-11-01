package com.eayesiltas.bookApp.request;

import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class  AuthorRequest {
    private String name;
    private String surname;
    private Gender gender;
    private String nationality;
    private String birthDate;
}
