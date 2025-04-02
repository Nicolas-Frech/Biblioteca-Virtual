package br.com.nicolasfrech.biblioteca_online.application.user.validation;

import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.infra.exception.ValidateException;

public class UserValidation {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new ValidateException("E-mail já registrado!");
        }
    }

    public void validateUsename(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new ValidateException("Usuário já registrado!");
        }
    }
}
