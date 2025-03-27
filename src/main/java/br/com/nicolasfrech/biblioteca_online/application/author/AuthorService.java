package br.com.nicolasfrech.biblioteca_online.application.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author registAuthor(AuthorDTO dto) {
        Author registerAuthor = new Author(dto);

        authorRepository.save(registerAuthor);
        return registerAuthor;
    }
}
