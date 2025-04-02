package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserRegistDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.validation.UserValidation;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserValidation userValidation;

    private final UserRepository userRepository;

    private final UserEntityMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(UserValidation userValidation, UserRepository userRepository, UserEntityMapper mapper) {
        this.userValidation = userValidation;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User registUser(UserRegistDTO dto) {
        userValidation.validateEmail(dto.email());
        userValidation.validateUsename(dto.username());

        var encodedPwd = passwordEncoder.encode(dto.password());
        User user = new User(dto.username(), encodedPwd, dto.email());

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return mapper.toEntity(user);
    }
}
