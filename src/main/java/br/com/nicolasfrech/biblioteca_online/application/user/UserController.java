package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserGenreDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping
    @Transactional
    public ResponseEntity updateUserRole(@RequestBody @Valid UserUpdateDTO dto) {
        User user = userService.updateUserRole(dto);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(Authentication authentication) {
        String role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity findUserByUsername(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @PutMapping("/{title}")
    @Transactional
    public ResponseEntity addBookToLibrary(@PathVariable String title, Principal principal) {
        User user = userService.addBookToLibrary(title, principal);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @DeleteMapping("/book/remove/{title}")
    @Transactional
    public ResponseEntity removeBookFromLibrary(@PathVariable String title, Principal principal) {
        User user = userService.removeBookFromLibrary(title, principal);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @GetMapping("/myLibrary/{title}")
    public ResponseEntity hasBookInLibrary(@PathVariable String title, Principal principal) {
        Boolean hasBook = userService.hasBookInMyLibrary(title, principal);

        return ResponseEntity.ok(hasBook);
    }

    @PutMapping("/upload-profile")
    @Transactional
    public ResponseEntity addProfileImage(@RequestParam("image") MultipartFile image, Principal principal) {
        String filename = userService.addProfileImage(image, principal);

        Map<String, String> response = new HashMap<>();
        response.put("fileName", filename);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/favoriteGenre/{genre}")
    @Transactional
    public ResponseEntity addFavoriteGenre(@PathVariable Genre genre, Principal principal) {
        String message = userService.addFavoriteGenre(genre, principal);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
}

