package com.eayesiltas.bookApp.repository;

import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.response.BookResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    // List<Book> findBooksByUserIdIsNull();
    List<Book> findBooksByAuthorId(Long authorId);
}
