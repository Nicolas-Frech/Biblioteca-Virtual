package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserUpdateDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.application.user.validation.UserValidation;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;

    public UserService(UserRepository userRepository, UserValidation userValidation) {
        this.userRepository = userRepository;
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
}
