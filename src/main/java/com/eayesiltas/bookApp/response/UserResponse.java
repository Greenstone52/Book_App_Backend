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
        name = user.getDetailsOfUser().getName();
        surname = user.getDetailsOfUser().getSurname();
        gender = user.getDetailsOfUser().getGender();
        birthDate = user.getDetailsOfUser().getBirthDate();
    }
}
