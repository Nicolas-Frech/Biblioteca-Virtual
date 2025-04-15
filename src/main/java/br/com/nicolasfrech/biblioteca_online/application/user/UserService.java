package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.validation.UserValidation;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserValidation userValidation;

    @Value("${upload.dir}")
    private String uploadDir;

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

    public User removeBookFromLibrary(String title, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        Book reservedBook = bookRepository.findByTitle(title);

        user.removeBookFromLibrary(reservedBook);
        userRepository.save(user);
        bookRepository.save(reservedBook);
        return user;
    }

    public Boolean hasBookInMyLibrary(String title, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        Book book = bookRepository.findByTitle(title);

        if(user.getMyLibrary().contains(book)) {
            return true;
        } else return false;
    }

    public String addProfileImage(MultipartFile image, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        try {
            if (image.isEmpty()) {
                return "Arquivo vazio.";
            }

            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir, filename).toAbsolutePath();
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            user.addProfileImage(filename);
            userRepository.save(user);

            return filename;

        } catch (IOException e) {
            return "Erro ao salvar imagem: " + e.getMessage();
        }
    }
}
