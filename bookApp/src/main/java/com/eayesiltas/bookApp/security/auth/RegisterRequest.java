package com.eayesiltas.bookApp.security.auth;

import com.eayesiltas.bookApp.enums.Gender;
import com.eayesiltas.bookApp.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private Gender gender;
    private String address;
    private String gsm;
    private String birthDate;
}
