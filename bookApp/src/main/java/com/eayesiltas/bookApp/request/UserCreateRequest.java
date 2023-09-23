package com.eayesiltas.bookApp.request;

import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class UserCreateRequest {
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long userId;
    private String email;
    private String password;
    // private Long userDetailsId;
    private String name;
    private String surname;
    private Gender gender;
    private String address;
    private String gsm;
    private String birthDate;
}
