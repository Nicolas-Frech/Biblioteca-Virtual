package br.com.nicolasfrech.biblioteca_online.application.user;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.exception.ValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private JacksonTester<UserReturnDTO> userReturnDTOJson;

    @Test
    @DisplayName("Should return 400 code for bad request")
    @WithMockUser
    void addBookToLibrary_scenary01() throws Exception {
        when(userService.addBookToLibrary(eq("title"), any()))
                .thenThrow(new ValidateException("User not found"));

        var response = mvc.perform(put("/user/title"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 code for correct request")
    void addBookToLibrary_scenary02() throws Exception {
        Book book = new Book(new BookDTO("title", Genre.FANTASY, "author", LocalDate.now(), "cover", "synopsis"));
        User user = new User("username", "password", "email@email.com");

        when(userService.addBookToLibrary(eq("title"), any())).thenReturn(user);

        var response = mvc.perform(put("/user/" + book.getTitle()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}