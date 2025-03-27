package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.author.AuthorValidator;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.validation.BookValidator;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private AuthorValidator authorValidator;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book registBook(BookDTO dto) {
        Book registerBook = new Book(dto);
        authorValidator.validateName(dto.authorName());

        Author author = authorRepository.findByName(dto.authorName());
        registerBook.addAuthor(author);
        author.addBook(registerBook);
        author.addGenre(dto.genre());

        bookRepository.save(registerBook);
        return registerBook;
    }

    public void deleteBook(Long id) {
        bookValidator.validateId(id);

        Book deletedBook = bookRepository.getReferenceById(id);
        deletedBook.deleteBook();
        bookRepository.save(deletedBook);
    }

    public Book findBookByTitle(String title) {
        bookValidator.validateTitle(title);

        Book book = bookRepository.findByTitle(title);
        return book;
    }

    public Book reserveBook(String title) {
        bookValidator.validateTitle(title);

        Book reservedBook = bookRepository.findByTitle(title);
        bookValidator.validateReserve(reservedBook);

        reservedBook.reserveBook();
        bookRepository.save(reservedBook);
        return reservedBook;
    }
}
