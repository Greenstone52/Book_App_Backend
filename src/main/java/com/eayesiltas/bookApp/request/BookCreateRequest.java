package com.eayesiltas.bookApp.request;

import lombok.Data;

@Data
public class BookCreateRequest {
    private String name;
    private int pageNumber;
    private Long authorId;
    private String text;
}
