package br.com.nicolasfrech.biblioteca_online.application.author;

import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;

public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validateName(String name) {
        if(!authorRepository.existsByNameAndActiveTrue(name)) {
            throw new RuntimeException("Não existe autor com esse nome!");
        }
    }

    public void validateId(Long id) {
        if(!authorRepository.existsByIdAndActiveTrue(id)) {
            throw new RuntimeException("Nãõ existe autor com esse ID!");
        }
    }
}
