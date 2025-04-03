package br.com.nicolasfrech.biblioteca_online.application.user.authentication;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserLoginDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserRegistDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.security.token.TokenDTO;
import br.com.nicolasfrech.biblioteca_online.infra.security.token.TokenService;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registUser(@RequestBody @Valid UserRegistDTO dto, UriComponentsBuilder uriBuilder) {
        User user = authService.registUser(dto);

        var uri = uriBuilder.path("/login/register/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserReturnDTO(user));
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var tokenJWT = tokenService.createToken((UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}
