package com.eayesiltas.bookApp.response;

import com.eayesiltas.bookApp.entity.Book;
import lombok.Data;

@Data
public class BookOfUsersResponse {
    private String bookName;
    private String authorName;

    public BookOfUsersResponse(Book book){
        this.bookName = book.getName();

        if(book.getAuthor() != null){
            this.authorName = book.getAuthor().getName() + " " + book.getAuthor().getSurname();
        }else{
            this.authorName = "Empty";
        }

    }
}
