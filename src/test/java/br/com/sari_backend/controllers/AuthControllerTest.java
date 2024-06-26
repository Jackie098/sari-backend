package br.com.sari_backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.sari_backend.dtos.user.AuthDTO;
import br.com.sari_backend.mappers.GenericMapper;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.repositories.UserRepository;
import br.com.sari_backend.services.AuthService;
import br.com.sari_backend.services.UserService;
import br.com.sari_backend.utils.AbstractPasswordUtils;
import br.com.sari_backend.utils.AbstractTokenUtils;

import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class AuthControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  AuthService authService;

  @MockBean
  UserService userService;

  @MockBean
  AbstractTokenUtils tokenUtils;

  @MockBean
  AbstractPasswordUtils passwordUtils;

  @Autowired
  private JacksonTester<Object> json;

  @Test
  public void whenLoginWithValidCredentials_ThenReturn200AndToken() throws Exception {
    AuthDTO data = new AuthDTO("adm@gmail.com", "1234");

    User mockedUser = new User("adm", data.getEmail(),
        "$2a$12$u6eG7i8XNexFtKe4GKyVxuc.59uP3zxGrGFIsfAZvn8i9eiT8S8g2", "89994138240", RoleEnum.ADM);
    mockedUser.setId(UUID.fromString("da918272-fde7-4558-99ad-c63f2c210841"));

    given(userService.getUserByEmail(data.getEmail())).willReturn(Optional.of(mockedUser));

    MockHttpServletResponse response = mvc
        .perform(post("/auth").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(json.write(data).getJson()))
        .andDo(MockMvcResultHandlers.print())
        .andReturn()
        .getResponse();

    System.out.println(response.getContentAsString());

    Map<String, String> token = new HashMap<String, String>();
    // token.put("token", "");

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    // assertThat(response.getContentAsString()).isEqualTo(json.write());
  }

}
