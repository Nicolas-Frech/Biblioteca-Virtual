package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/name/{username}")
    public ResponseEntity findUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }
}
