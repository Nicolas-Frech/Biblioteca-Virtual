package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
        return ResponseEntity.created(uri).body(registerBook);
    }
}
