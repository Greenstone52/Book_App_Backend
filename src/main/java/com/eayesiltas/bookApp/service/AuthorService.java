package com.eayesiltas.bookApp.service;

import com.eayesiltas.bookApp.entity.Author;
import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.repository.AuthorRepository;
import com.eayesiltas.bookApp.repository.BookRepository;
import com.eayesiltas.bookApp.request.AuthorRequest;
import com.eayesiltas.bookApp.request.AuthorUpdateRequest;
import com.eayesiltas.bookApp.request.BookUpdateRequest;
import com.eayesiltas.bookApp.response.AuthorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public List<AuthorResponse> getAllTheAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(author -> new AuthorResponse(author)).collect(Collectors.toList());
    }

    public String getAllTheBooksOfTheAuthor(Long id) {

        List<Book> books;
        String message = "";

        if (id != null) {

            try {
                books = bookRepository.findBooksByAuthorId(id);
                message += books.get(0).getAuthor().getName() + " "
                        + books.get(0).getAuthor().getSurname()
                        + " has " + books.size() + " books. These are : \n";

                for (int i = 0; i < books.size(); i++) {
                    message += books.get(i).getName() + "\n";
                }

                return message;
            } catch (IndexOutOfBoundsException exception) {
                Author author = authorRepository.findById(id).orElse(null);

                return author != null ?
                        "The author called " + author.getName() + " " + author.getSurname() + " has not any enrolled book in the our system!"
                        :
                        "There is no such the author in our system!";
            }

        } else {
            return "Please enter an available author!";
        }

    }

    public void createOneAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setSurname(authorRequest.getSurname());

        author.setGender(authorRequest.getGender());
        author.setNationality(authorRequest.getNationality());
        author.setBirthDate(authorRequest.getBirthDate());

        authorRepository.save(author);
    }

    public void updateTheAuthor(Long id, AuthorUpdateRequest authorUpdateRequest) {

        Author author = authorRepository.findById(id).orElse(null);
        author.setGender(authorUpdateRequest.getGender());
        author.setNationality(authorUpdateRequest.getNationality());
        author.setName(authorUpdateRequest.getName());
        author.setBirthDate(authorUpdateRequest.getBirthDate());
        author.setSurname(authorUpdateRequest.getSurname());

        authorRepository.save(author);
    }

    public String deleteOneAuthorById(Long id) {

        Author author = authorRepository.findById(id).orElse(null);
        String message = "The author called " + author.getName() + " " +author.getSurname() + " was removed from the system.";
        authorRepository.deleteById(id);
        return message;

    }

}
