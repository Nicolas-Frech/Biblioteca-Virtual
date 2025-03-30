package br.com.nicolasfrech.biblioteca_online.application.author.config;

import br.com.nicolasfrech.biblioteca_online.application.author.AuthorValidator;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorRepositoryImpl;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorRepositoryJPA;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorConfig {

    @Bean
    AuthorRepositoryImpl createAuthorRepositoryImpl(AuthorRepositoryJPA jpaRepository, AuthorEntityMapper mapper, BookEntityMapper bookEntityMapper) {
        return new AuthorRepositoryImpl(jpaRepository, mapper, bookEntityMapper);
    }

    @Bean
    AuthorEntityMapper createAuthorEntityMapper() {
        return new AuthorEntityMapper();
    }

    @Bean
    AuthorValidator createAuthorValidator(AuthorRepository authorRepository) {
        return new AuthorValidator(authorRepository);
    }
}
