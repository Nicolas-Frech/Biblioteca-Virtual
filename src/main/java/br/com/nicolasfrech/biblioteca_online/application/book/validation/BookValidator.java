package br.com.nicolasfrech.biblioteca_online.application.book.validation;

import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

public class BookValidator {

    private final BookRepository bookRepository;

    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validateTitle(String title) {
        if(!bookRepository.existsByTitleAndActiveTrue(title)) {
            throw new RuntimeException("Não existe livro com esse título!");
        }
    }

    public void validateId(Long id) {
        if(!bookRepository.existsByIdAndActiveTrue(id)) {
            throw new RuntimeException("Não existe livro com esse ID!");
        }
    }

    public void validateReserve(Book reservedBook) {
        if(reservedBook.getReserved()) {
            throw new RuntimeException("Este livro já está reservado!");
        }
    }
}
