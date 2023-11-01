package com.eayesiltas.bookApp.request;

import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class AuthorUpdateRequest {
    private String name;
    private String surname;
    private String birthDate;
    private Gender gender;
    private String nationality;
}
