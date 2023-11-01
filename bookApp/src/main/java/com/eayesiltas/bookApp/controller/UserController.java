package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.request.BookSetToUserRequest;
import com.eayesiltas.bookApp.request.UserDetailsUpdateRequest;
import com.eayesiltas.bookApp.request.UserSetTheCurrentBookRequest;
import com.eayesiltas.bookApp.response.AuthorResponse;
import com.eayesiltas.bookApp.response.BookOfUsersResponse;
import com.eayesiltas.bookApp.response.BookResponse;
import com.eayesiltas.bookApp.response.UserResponse;
import com.eayesiltas.bookApp.service.AuthorService;
import com.eayesiltas.bookApp.service.BookService;
import com.eayesiltas.bookApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
//@Hidden
public class UserController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/authors")
    public List<AuthorResponse> getAllTheAuthors(){
        return authorService.getAllTheAuthors();
    }

    @GetMapping("/authors/{id}")
    public String getAllTheBooksOfTheAuthor(@PathVariable Long id){
        return authorService.getAllTheBooksOfTheAuthor(id);
    }

    @GetMapping("/books")
    public List<BookResponse> getAllTheBooks(){
        return bookService.getAllTheBooks();
    }

    @PutMapping("/users/{id}")
    public UserResponse updateOneUserDetails(@PathVariable Long id, @RequestBody UserDetailsUpdateRequest request){
        return userService.updateOneUserDetails(id,request);
    }

    @PostMapping("/users/removeABookFromTheUser/{id}")
    public void removeOneBookFromTheUser(@PathVariable Long id,@RequestBody BookSetToUserRequest bookRequest){
        userService.deleteOneBookFromTheUser(id,bookRequest);
    }

    @PostMapping("/users/{userId}/setCurrentBook")
    public String setTheCurrentBookToTheUser(@PathVariable Long userId, @RequestBody UserSetTheCurrentBookRequest request){
        return userService.setTheCurrentBookToTheUser(userId,request);
    }

    @PostMapping("/users/setABook/{userId}")
    public String setABookToAUser(@PathVariable Long userId,@RequestBody BookSetToUserRequest bookRequest){
        return userService.setABookToAUser(userId,bookRequest);
    }

    @GetMapping("/users/{userId}/getCurrentBookChapters/{chapterId}")
    public String getChapterOfTheCurrentBookTheUserHas(@PathVariable Long userId,@PathVariable int chapterId){
        return userService.getTheChapterOfTheCurrentBook(userId,chapterId);
    }

    @GetMapping("/users/{id}/getAllBooksOfUser")
    public List<BookOfUsersResponse> getAllBooksOfUsers(@PathVariable Long id){
        return userService.getAllBooksOfUsers(id);
    }

}
