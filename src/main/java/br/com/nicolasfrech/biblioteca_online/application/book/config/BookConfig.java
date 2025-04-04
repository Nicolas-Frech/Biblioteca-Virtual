package br.com.nicolasfrech.biblioteca_online.application.book.config;

import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.validation.BookValidator;
import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookRepositoryImpl;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookRepositoryJPA;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    BookRepositoryImpl createBookRepositoryImpl(BookRepositoryJPA jpaRepository, BookEntityMapper mapper) {
        return new BookRepositoryImpl(jpaRepository, mapper);
    }

    @Bean
    BookEntityMapper createBookEntityMapper(AuthorEntityMapper mapper, UserEntityMapper userEntityMapper) {
        return new BookEntityMapper(mapper, userEntityMapper);
    }

    @Bean
    BookValidator createBookValidator(BookRepository bookRepository) {
        return new BookValidator(bookRepository);
    }
}
