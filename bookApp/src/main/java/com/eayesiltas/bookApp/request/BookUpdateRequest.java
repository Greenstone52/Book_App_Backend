package com.eayesiltas.bookApp.request;

import lombok.Data;

@Data
public class BookUpdateRequest {
      private String name;
      private int pageNumber;
      private String text;
      private Long authorId;
}
