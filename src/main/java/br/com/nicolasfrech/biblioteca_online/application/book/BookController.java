package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookRateDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReturnDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReviewDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDataSeeder seeder;

    @PostMapping
    @Transactional
    public ResponseEntity registBook(@RequestBody @Valid BookDTO dto, UriComponentsBuilder uriBuilder) {
        Book registerBook = bookService.registBook(dto);
        var uri = uriBuilder.path("/book/{id}").buildAndExpand(registerBook.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookReturnDTO(registerBook));
    }

    @DeleteMapping("/{title}")
    @Transactional
    public ResponseEntity deleteBook(@PathVariable String title) {
        bookService.deleteBook(title);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity findBookByTitle(@PathVariable String title, @PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var page = bookService.findAllByTitleAndActiveTrue(title, pagination);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity findAllBooks(@PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var page = bookService.findAllBooks(pagination);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity findBookByGenre(@PathVariable String genre, @PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var genreEnum = Genre.valueOf(genre);
        var page = bookService.findBookByGenre(genreEnum, pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/author/{name}")
    public ResponseEntity findBookByAuthor(@PathVariable String name, @PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var page = bookService.findBookByAuthor(name, pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity findBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok(new BookReturnDTO(book));
    }

    @PutMapping("/review")
    @Transactional
    public ResponseEntity addReview(@RequestBody BookReviewDTO dto, Principal principal) {
        String username = principal.getName();
        Book book = bookService.reviewBook(dto, username);
        return ResponseEntity.ok(new BookReturnDTO(book));
    }

    @PutMapping("/rate")
    @Transactional
    public ResponseEntity addRating(@RequestBody BookRateDTO dto) {
        Book book = bookService.addRating(dto);

        return ResponseEntity.ok(new BookReturnDTO(book));
    }

    @GetMapping("/seed")
    @Transactional
    public ResponseEntity seed() {
        seeder.seed();

        return ResponseEntity.ok().build();
    }
}
