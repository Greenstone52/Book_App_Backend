package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.request.BookSetToUserRequest;
import com.eayesiltas.bookApp.request.UserCreateRequest;
import com.eayesiltas.bookApp.request.UserDetailsUpdateRequest;
import com.eayesiltas.bookApp.request.UserSetTheCurrentBookRequest;
import com.eayesiltas.bookApp.response.BookOfUsersResponse;
import com.eayesiltas.bookApp.response.UserResponse;
import com.eayesiltas.bookApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    @GetMapping
    public List<UserResponse> getAllTheUsers(){
        return userService.getAllTheUsers();
    }

    @GetMapping("/{id}/getAllBooksOfUser")
    public List<BookOfUsersResponse> getAllBooksOfUsers(@PathVariable Long id){
        return userService.getAllBooksOfUsers(id);
    }

//    @GetMapping("/{userId}/books/{bookId}/chapter/{chapterId}")
//    public String getChapterOfTheBookTheUserHas(@PathVariable Long userId, @PathVariable Long bookId, @PathVariable int chapterId){
//        return userService.getChapterOfTheBookTheUserHas(userId,bookId,chapterId);
//    }

    @GetMapping("/{userId}/getCurrentBookChapters/{chapterId}")
    public String getChapterOfTheCurrentBookTheUserHas(@PathVariable Long userId,@PathVariable int chapterId){
        return userService.getTheChapterOfTheCurrentBook(userId,chapterId);
    }


    @PostMapping
    public UserResponse postOneUser(@RequestBody UserCreateRequest userCreateRequest){
        return userService.saveOneUser(userCreateRequest);
    }

    @PostMapping("/setABook/{userId}")
    public void setABookToAUser(@PathVariable Long userId,@RequestBody BookSetToUserRequest bookRequest){
        userService.setABookToAUser(userId,bookRequest);
    }

    @PostMapping("/{userId}/setCurrentBook")
    public String setTheCurrentBookToTheUser(@PathVariable Long userId, @RequestBody UserSetTheCurrentBookRequest request){
        return userService.setTheCurrentBookToTheUser(userId,request);
    }

    // @GetMapping("/getAllTheBooks/{id}")
    // public String getAllTheBooks(@PathVariable Long id){
    //     return userService.getAllTheBooksOfTheAssociatedUser(id);
    // }

    @PostMapping("/deleteABookFromTheUser/{id}")
    public void deleteOneBookFromTheUser(@PathVariable Long id,@RequestBody BookSetToUserRequest bookRequest){
        userService.deleteOneBookFromTheUser(id,bookRequest);
    }

    @PutMapping("/{id}")
    public UserResponse updateOneUserDetails(@PathVariable Long id, @RequestBody UserDetailsUpdateRequest request){
        return userService.updateOneUserDetails(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteOneUser(@PathVariable Long id){
        userService.deleteOneUserById(id);
    }
}
