package br.com.nicolasfrech.biblioteca_online.application.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
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
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthorControllerTest {

    @MockitoBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    JacksonTester<AuthorDTO> authorDTOJson;

    @Autowired
    JacksonTester<AuthorReturnDTO> authorReturnDTOJson;

    @Test
    @DisplayName("Should return 400 code for bad request")
    void regist_scenary01() throws Exception {
        var response = mvc.perform(post("/author")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 code for correct request")
    void regist_scenary02() throws Exception {
        var authorDTO = new AuthorDTO("Author", LocalDate.parse("1999-03-04"));

        when(authorService.registAuthor(any())).thenReturn(new Author(authorDTO));

        var response = mvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDTOJson.write(authorDTO).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var authorReturnDTO = new AuthorReturnDTO(
                null,
                authorDTO.name(),
                new ArrayList<>(),
                null,
                authorDTO.birthdate()
        );

        var expectedJson = authorReturnDTOJson.write(authorReturnDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

}