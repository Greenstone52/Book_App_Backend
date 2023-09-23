package com.eayesiltas.bookApp.controller;

import com.eayesiltas.bookApp.entity.Author;
import com.eayesiltas.bookApp.request.AuthorRequest;
import com.eayesiltas.bookApp.request.AuthorUpdateRequest;
import com.eayesiltas.bookApp.response.AuthorResponse;
import com.eayesiltas.bookApp.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    @GetMapping
    public List<AuthorResponse> getAllTheAuthors(){
        return authorService.getAllTheAuthors();
    }

    @GetMapping("/{id}")
    public String getAllTheBooksOfTheAuthor(@PathVariable Long id){
        return authorService.getAllTheBooksOfTheAuthor(id);
    }

    @PostMapping
    public void postOneAuthor(@RequestBody AuthorRequest authorRequest){
        authorService.createOneAuthor(authorRequest);
    }

    @PutMapping("/{id}")
    public void updateOneAuthor(@PathVariable Long id, @RequestBody AuthorUpdateRequest authorUpdateRequest){
        authorService.updateTheAuthor(id,authorUpdateRequest);
    }

    @DeleteMapping("/{authorId}")
    public String deleteOneAuthor(@PathVariable Long authorId){
        return authorService.deleteOneAuthorById(authorId);
    }

}
