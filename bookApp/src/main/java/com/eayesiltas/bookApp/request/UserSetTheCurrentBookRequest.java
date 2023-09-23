package com.eayesiltas.bookApp.request;

import lombok.Data;

@Data
public class UserSetTheCurrentBookRequest {
    private Long currentBookId;
}
