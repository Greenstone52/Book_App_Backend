package com.eayesiltas.bookApp.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String email;
    private String currentPassword;
    private String verifyPassword;
    private String newPassword;
}
