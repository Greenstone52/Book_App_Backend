package com.eayesiltas.bookApp.response;

import com.eayesiltas.bookApp.entity.Book;
import lombok.Data;

@Data
public class BookResponse {
    private String name;
    private String authorNameAndSurname;
    private String text;
    // private String userNameAndSurname;
    private int pageNumber;
    // private boolean isAvailable;

    public BookResponse(Book book){
        name = book.getName();
        text = book.getText();

        if(book.getAuthor() == null){
            authorNameAndSurname = "";
        }else{
            authorNameAndSurname = book.getAuthor().getName() + " " + book.getAuthor().getSurname();
        }

//        if(book.getUser() == null){
//            userNameAndSurname = "";
//        }else{
//            userNameAndSurname = book.getUser().getUserDetails().getName() + " " + book.getUser().getUserDetails().getSurname();
//        }

        pageNumber = book.getPageNumber();
//        isAvailable = book.getUser() == null;
    }
}
