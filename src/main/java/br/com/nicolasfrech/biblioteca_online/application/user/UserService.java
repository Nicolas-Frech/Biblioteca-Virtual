package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.validation.UserValidation;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserValidation userValidation;

    public UserService(UserRepository userRepository, BookRepository bookRepository, UserValidation userValidation) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userValidation = userValidation;
    }

    public User updateUserRole(UserUpdateDTO dto) {
        userValidation.validateUsernameForLogin(dto.username());

        User user = userRepository.findByUsername(dto.username());
        user.changeRole(dto.role());

        userRepository.save(user);
        return user;
    }

    public User findUserByUsername(String username) {
        userValidation.validateUsernameForLogin(username);

        User user = userRepository.findByUsername(username);
        return user;
    }

    public User addBookToLibrary(String title, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        Book reservedBook = bookRepository.findByTitle(title);

        user.addBookToLibrary(reservedBook);
        userRepository.save(user);
        bookRepository.save(reservedBook);
        return user;
    }
}
