package br.com.nicolasfrech.biblioteca_online.application.user.authentication;

import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserRegistDTO;
import br.com.nicolasfrech.biblioteca_online.application.user.dto.UserReturnDTO;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.domain.user.UserRole;
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

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private JacksonTester<UserRegistDTO> userRegistDTOJson;

    @Autowired
    private JacksonTester<UserReturnDTO> userReturnDTOJson;

    @Test
    @DisplayName("Should return 400 code for bad request")
    void regist_scenary01() throws Exception {
        var response = mvc.perform(post("/login/register")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 201 code for correct request")
    void regist_scenary02() throws Exception {
        var userDTO = new UserRegistDTO("username", "password", "email@email.com");

        when(authService.registUser(any())).thenReturn(new User("username", "password", "email@email.com"));

        var response =  mvc.perform(post("/login/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistDTOJson.write(userDTO).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var userReturnDTO = new UserReturnDTO(null, "username", "password", new HashSet<>(), "email@email.com", UserRole.USER);

        var expectedJson = userReturnDTOJson.write(userReturnDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}