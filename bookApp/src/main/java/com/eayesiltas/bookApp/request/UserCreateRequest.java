package com.eayesiltas.bookApp.request;

import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String email;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private String address;
    private String gsm;
    private String birthDate;
}
