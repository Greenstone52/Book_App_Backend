package com.eayesiltas.bookApp.service;

import com.eayesiltas.bookApp.entity.Book;
import com.eayesiltas.bookApp.entity.User;
import com.eayesiltas.bookApp.entity.UserDetails;
import com.eayesiltas.bookApp.repository.BookRepository;
import com.eayesiltas.bookApp.repository.UserDetailsRepository;
import com.eayesiltas.bookApp.repository.UserRepository;
import com.eayesiltas.bookApp.request.BookSetToUserRequest;
import com.eayesiltas.bookApp.request.UserCreateRequest;
import com.eayesiltas.bookApp.request.UserDetailsUpdateRequest;
import com.eayesiltas.bookApp.request.UserSetTheCurrentBookRequest;
import com.eayesiltas.bookApp.response.BookOfUsersResponse;
import com.eayesiltas.bookApp.response.BookResponse;
import com.eayesiltas.bookApp.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private BookRepository bookRepository;
    private BookService bookService;

    public List<UserResponse> getAllTheUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(p->new UserResponse(p)).collect(Collectors.toList());
    }

    public UserResponse saveOneUser(UserCreateRequest newUserRequest){

        User newUser = new User();
        // newUser.setId(newUserRequest.get);
        newUser.setEmail(newUserRequest.getEmail());
        newUser.setPassword(newUserRequest.getPassword());

        UserDetails newUserDetails = new UserDetails();
        // newUserDetails.setId(newUserRequest.getUserDetailsId());
        newUserDetails.setGsm(newUserRequest.getGsm());
        newUserDetails.setGender(newUserRequest.getGender());
        newUserDetails.setName(newUserRequest.getName());
        newUserDetails.setSurname(newUserRequest.getSurname());
        newUserDetails.setAddress(newUserRequest.getAddress());
        newUserDetails.setBirthDate(newUserRequest.getBirthDate());
        userDetailsRepository.save(newUserDetails);

        newUser.setUserDetails(newUserDetails);
        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser);
        return userResponse;
    }

    public void setABookToAUser(Long id,BookSetToUserRequest bookRequest){
        User user = userRepository.findById(id).orElse(null);
        Book book = bookRepository.findById(bookRequest.getId()).orElse(null);

        if(bookRequest.getId() != null){
            user.getBooks().add(book);
            book.getUsers().add(user);

            userRepository.save(user);
            bookRepository.save(book);
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
                UserDetails userDetails = user.getUserDetails();
                userDetails.setCurrentBookId(setBookRequest.getCurrentBookId());
                userDetailsRepository.save(userDetails);
                return "The operation was completed successfully!";
            }else{
                return "The user has not this book!";
            }
        }

    }

    //public String getAllTheBooksOfTheAssociatedUser(Long id){
    //    User user = userRepository.findById(id).orElse(null);
    //
    //    String[][] books = new String[user.getBooks().size()][2];
    //    String result = "";
    //
    //    for(int i = 0; i<books.length; i++){
    //        books[i][0] = user.getBooks().get(i).getName();
    //        result += "Name: " + books[i][0] + "\n";
    //
    //        if(user.getBooks().get(i).getAuthor() != null){
    //            books[i][1] = "Author" + user.getBooks().get(i).getAuthor().getName();
    //            result += books[i][1];
    //        }
    //
    //        books[i][1] = "Author is null!\n";
    //        result += books[i][1];
    //    }
    //
    //    return result;
    //
    //}

    public List<BookOfUsersResponse> getAllBooksOfUsers(Long id){

        User user = userRepository.findById(id).orElse(null);
        List<Book> books = user.getBooks();

        return books.stream().map(book -> new BookOfUsersResponse(book)).collect(Collectors.toList());
    }

//    public String getChapterOfTheBookTheUserHas(Long userId,Long bookId,int chapterId){
//        User user = userRepository.findById(userId).orElse(null);
//        Book book = bookRepository.findById(bookId).orElse(null);
//
//        int counter = 0;
//
//        if(user == null){
//            return "The user is not enrolled in our system.";
//        }
//
//        boolean isHasTheBook = false;
//
//        if(book == null){
//            return "There is not a book like this.";
//        }else{
//            for(int i = 0; i<user.getBooks().size();i++){
//                if(book == user.getBooks().get(i)){
//                    isHasTheBook = true;
//                    counter = i;
//                    break;
//                }
//            }
//
//            if(isHasTheBook){
//                return bookService.getChapters(bookId,chapterId);
//            }else{
//                return "The user has not the book!";
//            }
//        }
//    }

    public String getTheChapterOfTheCurrentBook(Long userId,int chapterId){
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(user.getUserDetails().getCurrentBookId()).orElse(null);

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
        UserDetails userDetails = userDetailsRepository.findById(id).orElse(null);
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
