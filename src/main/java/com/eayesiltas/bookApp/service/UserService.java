package com.eayesiltas.bookApp.service;

import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.entity.DetailsOfUser;
import com.eayesiltas.bookApp.entity.User;
import com.eayesiltas.bookApp.repository.BookRepository;
import com.eayesiltas.bookApp.repository.DetailsOfUserRepository;
import com.eayesiltas.bookApp.repository.UserRepository;
import com.eayesiltas.bookApp.request.BookSetToUserRequest;
import com.eayesiltas.bookApp.request.UserCreateRequest;
import com.eayesiltas.bookApp.request.UserDetailsUpdateRequest;
import com.eayesiltas.bookApp.request.UserSetTheCurrentBookRequest;
import com.eayesiltas.bookApp.response.BookOfUsersResponse;
import com.eayesiltas.bookApp.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private DetailsOfUserRepository userDetailsRepository;
    private BookRepository bookRepository;
    private BookService bookService;

    public List<UserResponse> getAllTheUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(p->new UserResponse(p)).collect(Collectors.toList());
    }

    public UserResponse saveOneUser(UserCreateRequest newUserRequest){

        User newUser = new User();
        newUser.setEmail(newUserRequest.getEmail());
        newUser.setPassword(newUserRequest.getPassword());

        DetailsOfUser newDetailsOfUser = new DetailsOfUser();
        newDetailsOfUser.setGsm(newUserRequest.getGsm());
        newDetailsOfUser.setGender(newUserRequest.getGender());
        newDetailsOfUser.setName(newUserRequest.getName());
        newDetailsOfUser.setSurname(newUserRequest.getSurname());
        newDetailsOfUser.setAddress(newUserRequest.getAddress());
        newDetailsOfUser.setBirthDate(newUserRequest.getBirthDate());
        userDetailsRepository.save(newDetailsOfUser);

        newUser.setDetailsOfUser(newDetailsOfUser);
        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser);
        return userResponse;
    }

    public String setABookToAUser(Long id,BookSetToUserRequest bookRequest){
        User user = userRepository.findById(id).orElse(null);
        Book book = bookRepository.findById(bookRequest.getId()).orElse(null);

        boolean isAlreadyHas = false;

        if(user == null){
            return "There is not like a user has this id!";
        }else{
            for (int i = 0; i < user.getBooks().size(); i++) {
                if(user.getBooks().get(i).getId() == bookRequest.getId()){
                    isAlreadyHas = true;
                }
            }

            if(bookRequest.getId() != null && !isAlreadyHas){
                user.getBooks().add(book);
                book.getUsers().add(user);

                userRepository.save(user);
                bookRepository.save(book);

                return "The book was activated for the user!";

            }else if(isAlreadyHas){
                return "The user already has this book!";
            }else{
                return "There is no such a book!";
            }
        }

    }

    public String setTheCurrentBookToTheUser(Long userId, UserSetTheCurrentBookRequest setBookRequest){
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(setBookRequest.getCurrentBookId()).orElse(null);

        if(book == null){
            return "There is no such book in our system.";
        }else{
            boolean isHasTheUser = false;

            for(int i = 0; i < user.getBooks().size(); i++){
                if(user.getBooks().get(i) == book){
                    isHasTheUser = true;
                    break;
                }
            }

            if(isHasTheUser){
                DetailsOfUser userDetails = user.getDetailsOfUser();
                userDetails.setCurrentBookId(setBookRequest.getCurrentBookId());
                userDetailsRepository.save(userDetails);
                return "The operation was completed successfully!";
            }else{
                return "The user has not this book!";
            }
        }

    }

    public List<BookOfUsersResponse> getAllBooksOfUsers(Long id){

        User user = userRepository.findById(id).orElse(null);
        List<Book> books = user.getBooks();

        return books.stream().map(book -> new BookOfUsersResponse(book)).collect(Collectors.toList());
    }

    public String getTheChapterOfTheCurrentBook(Long userId,int chapterId){
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(user.getDetailsOfUser().getCurrentBookId()).orElse(null);

        return bookService.getChapters(book.getId(),chapterId);
    }

    public void deleteOneBookFromTheUser(Long id,BookSetToUserRequest bookRequest){

        User user = userRepository.findById(id).orElse(null);

        if(bookRequest.getId() != null){
            Book book = bookRepository.findById(bookRequest.getId()).orElse(null);

            for(int i = 0; i<user.getBooks().size(); i++){
                if(user.getBooks().get(i) == book){
                    user.getBooks().remove(book);
                    book.getUsers().remove(user);
                }
            }

            userRepository.save(user);
            bookRepository.save(book);
        }
    }

    public UserResponse updateOneUserDetails(Long id, UserDetailsUpdateRequest request){
        DetailsOfUser userDetails = userDetailsRepository.findById(id).orElse(null);
        userDetails.setGsm(request.getGsm());
        userDetails.setGender(request.getGender());
        userDetails.setSurname(request.getSurname());
        userDetails.setAddress(request.getAddress());
        userDetails.setBirthDate(request.getBirthDate());
        userDetails.setName(request.getName());

        userDetailsRepository.save(userDetails);
        UserResponse response = new UserResponse(userRepository.findById(userDetails.getId()).orElse(null));
        return response;
    }

    public void deleteOneUserById(Long id){
        userRepository.deleteById(id);
    }

}
