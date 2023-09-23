package com.eayesiltas.bookApp.response;

import com.eayesiltas.bookApp.entity.Book;
import lombok.Data;

@Data
public class BookAvailableResponse {
    private String name;
    private String authorNameAndSurname;
    private int pageNumber;

    public BookAvailableResponse(Book book){
        name = book.getName();

        if(book.getAuthor() == null){
            authorNameAndSurname = "Ali Desidero";
        }

        // authorNameAndSurname = book.getAuthor().getName() + " " + book.getAuthor().getSurname();
        pageNumber = book.getPageNumber();
    }
}
