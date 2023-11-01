package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.request.AuthorRequest;
import com.eayesiltas.bookApp.request.AuthorUpdateRequest;
import com.eayesiltas.bookApp.request.BookCreateRequest;
import com.eayesiltas.bookApp.request.BookUpdateRequest;
import com.eayesiltas.bookApp.service.AuthorService;
import com.eayesiltas.bookApp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping("/authors")
    public void postOneAuthor(@RequestBody AuthorRequest authorRequest){
        authorService.createOneAuthor(authorRequest);
    }

    @PutMapping("/authors/{id}")
    public void updateOneAuthor(@PathVariable Long id, @RequestBody AuthorUpdateRequest authorUpdateRequest){
        authorService.updateTheAuthor(id,authorUpdateRequest);
    }

    @DeleteMapping("/authors/{authorId}")
    public String deleteOneAuthor(@PathVariable Long authorId){
        return authorService.deleteOneAuthorById(authorId);
    }

    @GetMapping("/books/{bookId}/chapter/{id}")
    public String getSelectedChapterOfABook(@PathVariable Long bookId,@PathVariable int id){
        return bookService.getChapters(bookId,id);
    }

    @PostMapping("/books")
    public Book postOneBook(@RequestBody BookCreateRequest bookRequest){
        return bookService.postOneBook(bookRequest);
    }

    @PutMapping("/books/{bookId}")
    public void updateOneBook(@PathVariable Long bookId, @RequestBody BookUpdateRequest bookUpdateRequest){
        bookService.updateOneBook(bookId,bookUpdateRequest);
    }


}
