package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.response.UserResponse;
import com.eayesiltas.bookApp.service.BookService;
import com.eayesiltas.bookApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/books/stats/{bookId}")
    public String bookStats(@PathVariable Long bookId){
        return bookService.bookStats(bookId);
    }

    @DeleteMapping("/books/{bookId}")
    public String deleteOneBook(@PathVariable Long bookId){
        return bookService.deleteOneBookById(bookId);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllTheUsers(){
        return userService.getAllTheUsers();
    }

    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable Long id){
        userService.deleteOneUserById(id);
    }
}
