package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


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
}
