package com.eayesiltas.bookApp.response;

import com.eayesiltas.bookApp.entity.User;
import com.eayesiltas.bookApp.enums.Gender;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String surname;
    private Gender gender;
    private String birthDate;

    public UserResponse(User user){
        name = user.getUserDetails().getName();
        surname = user.getUserDetails().getSurname();
        gender = user.getUserDetails().getGender();
        birthDate = user.getUserDetails().getBirthDate();
    }
}
