package com.eayesiltas.bookApp.service;

import com.eayesiltas.bookApp.algorithm.ChapterAlgorithm;
import com.eayesiltas.bookApp.entity.Author;
import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.entity.User;
import com.eayesiltas.bookApp.repository.AuthorRepository;
import com.eayesiltas.bookApp.repository.BookRepository;
import com.eayesiltas.bookApp.repository.UserRepository;
import com.eayesiltas.bookApp.request.BookCreateRequest;
import com.eayesiltas.bookApp.request.BookUpdateRequest;
import com.eayesiltas.bookApp.response.BookResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private UserRepository userRepository;
    private ChapterAlgorithm chapterAlgorithm;

    public BookService(BookRepository bookRepository,AuthorRepository authorRepository,
                       UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    public List<BookResponse> getAllTheBooks(){
        List<Book> books = bookRepository.findAll();
        return books.stream().map(p-> new BookResponse(p)).collect(Collectors.toList());
    }

    public String bookStats(Long id){
        Book book = bookRepository.findById(id).orElse(null);

        if(book.getUsers().size() == 0){
            return "Nobody read this book yet!";
        }else if(book.getUsers().size() == 1){
            return String.valueOf(book.getUsers().size()) + " person read this book.";
        }else{
            return String.valueOf(book.getUsers().size()) + " persons read this book.";
        }
    }

    public String getChapters(Long bookId,int chapterNo){

        Book book = bookRepository.findById(bookId).orElse(null);

        chapterAlgorithm = new ChapterAlgorithm(book.getText(),book.getChapters());
        book.setChapters(chapterAlgorithm.getLastResult());

        if (chapterNo > book.getChapters().size() || chapterNo <= 0 ) {
            return "The chapter number you entered is not suitable!";
        }else{
            return book != null ?  book.getChapters().get(chapterNo-1) : "The book's text is empty!";
        }

    }

    public Book postOneBook(BookCreateRequest bookRequest){

         Book book = new Book();

         Author author;

         if(bookRequest.getAuthorId() == null){
            author = null;
         }else{
             author = authorRepository.findById(bookRequest.getAuthorId()).get();
         }

         book.setAuthor(author);
         book.setPageNumber(bookRequest.getPageNumber());
         book.setName(bookRequest.getName());
         book.setText(bookRequest.getText());
         bookRepository.save(book);
         return book;
    }

    public void updateOneBook(Long id, BookUpdateRequest bookUpdateRequest){

        Book book = bookRepository.findById(id).orElse(null);
        book.setName(bookUpdateRequest.getName());
        book.setAuthor(authorRepository.findById(bookUpdateRequest.getAuthorId()).orElse(null));
        book.setText(bookUpdateRequest.getText());
        book.setPageNumber(bookUpdateRequest.getPageNumber());

        bookRepository.save(book);
    }

    public String deleteOneBookById(Long id){

        Book book = bookRepository.findById(id).orElse(null);
        List<User> users = userRepository.findAll();

        for(int i=0; i<users.size();i++){
            for(int j=0; j<users.get(i).getBooks().size(); j++){
                users.get(i).getBooks().remove(book);
                userRepository.save(users.get(i));
            }
        }

        String message = "The book called "+ book.getName() + " was removed from the system.";
        bookRepository.deleteById(id);
        return message;

    }

}
