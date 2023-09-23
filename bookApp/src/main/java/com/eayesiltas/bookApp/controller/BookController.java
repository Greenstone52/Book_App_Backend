package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.request.BookCreateRequest;
import com.eayesiltas.bookApp.request.BookUpdateRequest;
import com.eayesiltas.bookApp.response.BookResponse;
import com.eayesiltas.bookApp.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @GetMapping
    public List<BookResponse> getAllTheBooks(){
        return bookService.getAllTheBooks();
    }

    //@GetMapping("/available")
    //public List<BookAvailableResponse> getAllTheAvailableBooks(){
    //    return bookService.getAllTheAvailableBooks();
    //}

    //@PatchMapping("/{userId}")
    //public void giveAnUserToABook(@RequestBody BookUpdateRequest bookUpdateRequest, @PathVariable Long userId){
    //    bookService.updateOneBooksUser(bookUpdateRequest,userId);
    //}

    @GetMapping("/stats/{bookId}")
    public String bookStats(@PathVariable Long bookId){
        return bookService.bookStats(bookId);
    }

    @GetMapping("/{bookId}/chapter/{id}")
    public String getSelectedChapterOfABook(@PathVariable Long bookId,@PathVariable int id){
        return bookService.getChapters(bookId,id);
    }

    @PostMapping
    public Book postOneBook(@RequestBody BookCreateRequest bookRequest){
        return bookService.postOneBook(bookRequest);
    }

    @PutMapping("/{bookId}")
    public void updateOneBook(@PathVariable Long bookId, @RequestBody BookUpdateRequest bookUpdateRequest){
        bookService.updateOneBook(bookId,bookUpdateRequest);
    }
    @DeleteMapping("/{bookId}")
    public String deleteOneBook(@PathVariable Long bookId){
        return bookService.deleteOneBookById(bookId);
    }

}
