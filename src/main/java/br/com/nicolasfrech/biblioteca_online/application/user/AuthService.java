package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserRegistDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.validation.UserValidation;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {

    private final UserValidation userValidation;

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(UserValidation userValidation, UserRepository userRepository) {
        this.userValidation = userValidation;
        this.userRepository = userRepository;
    }

    public User registUser(UserRegistDTO dto) {
        userValidation.validateEmail(dto.email());
        userValidation.validateUsename(dto.username());

        var encodedPwd = passwordEncoder.encode(dto.password());
        User user = new User(dto.username(), encodedPwd, dto.email());

        userRepository.save(user);
        return user;
    }
}
