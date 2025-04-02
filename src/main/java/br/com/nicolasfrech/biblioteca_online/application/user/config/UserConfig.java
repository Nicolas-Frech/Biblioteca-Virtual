package br.com.nicolasfrech.biblioteca_online.application.user.config;

import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserRepositoryImpl;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserRepositoryJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    UserRepositoryImpl createUserRepositoryImpl(UserRepositoryJPA jpaRepository, UserEntityMapper mapper) {
        return new UserRepositoryImpl(jpaRepository, mapper);
    }

    @Bean
   UserEntityMapper createUserEntityMapper() {
        return new UserEntityMapper();
    }
}
