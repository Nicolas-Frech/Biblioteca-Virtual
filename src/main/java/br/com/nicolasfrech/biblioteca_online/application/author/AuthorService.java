package br.com.nicolasfrech.biblioteca_online.application.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorValidator authorValidator;


    public AuthorService(AuthorRepository authorRepository, AuthorValidator authorValidator) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
    }

    public Author registAuthor(AuthorDTO dto) {
        Author registerAuthor = new Author(dto);

        authorRepository.save(registerAuthor);
        return registerAuthor;
    }

    public void deleteAuthor(Long id) {
        authorValidator.validateId(id);

        Author deletedAuthor = authorRepository.getReferenceById(id);
        deletedAuthor.deleteAuthor();
        authorRepository.save(deletedAuthor);
    }

    public Author findAuthorByName(String name) {
        authorValidator.validateName(name);

        Author author = authorRepository.findByNameWithBooks(name);

        return author;
    }
}
