package br.com.nicolasfrech.biblioteca_online.application.book;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @MockitoBean
    private BookService bookService;

    @Autowired
    private JacksonTester<BookDTO> bookDTOJson;

    @Autowired
    private JacksonTester<BookReturnDTO> bookReturnDTOJson;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should return 400 code for bad request")
    @WithMockUser(roles = "ADMIN")
    void regist_scenary01() throws Exception {
        var response = mvc.perform(post("/book")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 201 code for correct request")
    @WithMockUser(roles = "ADMIN")
    void regist_scenary02() throws Exception {
        var bookDTO = new BookDTO("Book", Genre.ADVENTURE, "Author", LocalDate.parse("1990-03-04"), "Cover", "Synopsis");
        var authorDTO = new AuthorDTO("Author", LocalDate.parse("1999-02-01"));

        Author author = new Author(authorDTO);
        Book book = new Book(bookDTO);
        book.addAuthor(author);

        when(bookService.registBook(any())).thenReturn(book);

        var response = mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson.write(bookDTO).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var bookReturnDTO = new BookReturnDTO(
                null,
                "Book",
                Genre.ADVENTURE,
                "Author",
                LocalDate.parse("1990-03-04"),
                "Cover",
                "Synopsis",
                new ArrayList<>(),
                0.0
        );

        var expectedJson = bookReturnDTOJson.write(bookReturnDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}

