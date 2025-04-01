package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReturnDTO;
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

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

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

    @GetMapping("/{title}")
    public ResponseEntity findBookByTitle(@PathVariable String title, @PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var page = bookService.findAllByTitleAndActiveTrue(title, pagination);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity findAllBooks(@PageableDefault(size = 20, sort = {"title"}) Pageable pagination) {
        var page = bookService.findAllBooks(pagination);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity reserveBook(@RequestBody @Valid String title) {
        Book reservedBook = bookService.reserveBook(title);

        return ResponseEntity.ok(new BookReturnDTO(reservedBook));
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
}
