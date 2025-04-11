package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.author.AuthorValidator;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookRateDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReviewDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.validation.BookValidator;
import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    private final BookValidator bookValidator;
    private final AuthorValidator authorValidator;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository, BookValidator bookValidator, AuthorValidator authorValidator) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.bookValidator = bookValidator;
        this.authorValidator = authorValidator;
    }

    public Book registBook(BookDTO dto) {
        Book registerBook = new Book(dto);
        authorValidator.validateName(dto.authorName());

        Author author = authorRepository.findByName(dto.authorName());
        registerBook.addAuthor(author);

        bookRepository.save(registerBook);
        return registerBook;
    }

    public Book findBookById(Long id) {
        bookValidator.validateId(id);

        Book book = bookRepository.findByIdAndActiveTrue(id);
        return book;
    }

    public void deleteBook(String title) {
        bookValidator.validateTitle(title);

        Book deletedBook = bookRepository.findByTitleAndActiveTrue(title);
        deletedBook.deleteBook();
        bookRepository.save(deletedBook);
    }

    public Page<BookReturnDTO> findAllBooks(Pageable pagination) {
        var page = bookRepository.findAllByActiveTrue(pagination).map(BookReturnDTO::new);
        return page;
    }

    public Page<BookReturnDTO> findAllByTitleAndActiveTrue(String title, Pageable pagination) {
        var page = bookRepository.findAllByTitleAndActiveTrue(title, pagination).map(BookReturnDTO::new);
        return page;
    }

    public Page<BookReturnDTO> findBookByGenre(Genre genre, Pageable pagination) {
        var page = bookRepository.findAllByGenreAndActiveTrue(genre, pagination).map(BookReturnDTO::new);
        return page;
    }

    public Page<BookReturnDTO> findBookByAuthor(String name, Pageable pagination) {
        var page = bookRepository.findAllByAuthorAndActiveTrue(name, pagination).map(BookReturnDTO::new);
        return page;
    }

    public Book reviewBook(BookReviewDTO dto, String username) {
        bookValidator.validateTitle(dto.title());

        Book book = bookRepository.findByTitle(dto.title());

        book.addReview(username, dto.review());
        bookRepository.save(book);
        return book;
    }

    public Book addRating(BookRateDTO dto) {
        bookValidator.validateTitle(dto.title());

        Book book = bookRepository.findByTitle(dto.title());

        List<Integer> updatedRatings = new ArrayList<>(book.getRatings());

        updatedRatings.add(dto.rating());

        book.setRatings(updatedRatings);
        bookRepository.save(book);
        return book;
    }
}
