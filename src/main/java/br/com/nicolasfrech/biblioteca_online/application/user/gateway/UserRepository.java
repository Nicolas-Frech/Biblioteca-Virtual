package br.com.nicolasfrech.biblioteca_online.application.user.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.user.User;

public interface UserRepository {

    User save(User user);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
