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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class BookControllerTest {

    @MockitoBean
    BookService bookService;

    @MockitoBean
    Book book;

    @Autowired
    JacksonTester<BookDTO> bookDTOJson;

    @Autowired
    JacksonTester<BookReturnDTO> bookReturnDTOJson;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Should return 400 code for bad request")
    void regist_scenary01() throws Exception {
        var response = mvc.perform(post("/book")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 code for correct request")
    void regist_scenary02() throws Exception {
        var bookDTO = new BookDTO("Book", Genre.ADVENTURE, "Author", LocalDate.parse("1990-03-04"), "Cover", "Synopsis");

        when(book.getAuthor().getName()).thenReturn("Author");
        when(bookService.registBook(any())).thenReturn(new Book(bookDTO));

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
                "Synospis",
                false
        );

        var expectedJson = bookReturnDTOJson.write(bookReturnDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}

