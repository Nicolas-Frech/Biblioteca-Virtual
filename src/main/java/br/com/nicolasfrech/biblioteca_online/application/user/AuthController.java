package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserRegistDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registUser(UserRegistDTO dto, UriComponentsBuilder uriBuilder) {
        User user = authService.registUser(dto);

        var uri = uriBuilder.path("/login/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserReturnDTO(user));
    }
}
