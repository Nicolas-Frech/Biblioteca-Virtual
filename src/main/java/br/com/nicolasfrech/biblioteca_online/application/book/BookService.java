package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book registBook(BookDTO dto) {
        Book registerBook = new Book(dto);
        Author author = authorRepository.findByName(dto.authorName());
        registerBook.addAuthor(author);

        author.addBook(registerBook);
        author.addGenre(dto.genre());

        bookRepository.save(registerBook);
        return registerBook;
    }
}
