package br.com.nicolasfrech.biblioteca_online.application.author.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.author.Author;

public interface AuthorRepository {

    Author findByName(String name);
}
