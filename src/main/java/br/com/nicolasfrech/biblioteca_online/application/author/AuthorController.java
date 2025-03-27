package br.com.nicolasfrech.biblioteca_online.application.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    @PostMapping
    @Transactional
    public ResponseEntity registAuthor(@RequestBody @Valid AuthorDTO dto, UriComponentsBuilder uriBuilder) {
        Author registerAuthor = authorService.registAuthor(dto);
        var uri = uriBuilder.path("/author/{id}").buildAndExpand(registerAuthor.getId()).toUri();
        return ResponseEntity.created(uri).body(registerAuthor);
    }
}
