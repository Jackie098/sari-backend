package br.com.sari_backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import br.com.sari_backend.dtos.user.AuthDTO;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.AuthService;
import br.com.sari_backend.services.IUserService;
import br.com.sari_backend.services.UserService;
import br.com.sari_backend.utils.AbstractPasswordUtils;
import br.com.sari_backend.utils.AbstractTokenUtils;
import br.com.sari_backend.utils.PasswordUtils;
import br.com.sari_backend.utils.TokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class AuthControllerTest {
  @Autowired
  private MockMvc mvc;

  // @MockBean
  // AuthService authService;

  // @MockBean
  // TokenUtils tokenUtils;

  // @MockBean
  // PasswordUtils passwordUtils;

  // @MockBean
  // IUserService userService;

  private AuthService authService;
  private IUserService userService;
  private AbstractTokenUtils tokenUtils;
  private AbstractPasswordUtils passwordUtils;

  @Autowired
  private JacksonTester<Object> json;

  @Before
  public void setup() {
    userService = Mockito.mock(UserService.class);
    tokenUtils = Mockito.mock(TokenUtils.class);
    passwordUtils = Mockito.mock(PasswordUtils.class);

    authService = new AuthService();
    authService.setUserService(userService);
    authService.setTokenUtils(tokenUtils);
    authService.setPasswordUtils(passwordUtils);
  }

  @Test
  public void whenLoginWithValidCredentials_ThenReturn200AndToken() throws Exception {
    AuthDTO data = new AuthDTO("adm@gmail.com", "1234");

    User mockedUser = new User("adm", data.getEmail(),
        "$2a$12$u6eG7i8XNexFtKe4GKyVxuc.59uP3zxGrGFIsfAZvn8i9eiT8S8g2", "89994138240", RoleEnum.ADM);
    mockedUser.setId(UUID.fromString("da918272-fde7-4558-99ad-c63f2c210841"));

    when(userService.getUserByEmail(data.getEmail())).thenReturn(Optional.of(mockedUser));

    when(authService.login(any(User.class))).thenCallRealMethod();
    when(passwordUtils.checkPass(any(String.class), any(String.class))).thenCallRealMethod();
    when(tokenUtils.generateToken(any(String.class), any(Date.class), any(User.class), any(String.class)))
        .thenCallRealMethod();

    MockHttpServletResponse response = mvc
        .perform(post("/auth").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(json.write(data).getJson()))
        .andDo(MockMvcResultHandlers.print())
        .andReturn()
        .getResponse();

    System.out.println("response.getContentAsString() - " + response.getContentAsString());

    Map<String, String> token = new HashMap<String, String>();
    // token.put("token", "");

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    // assertThat(response.getContentAsString()).isEqualTo(json.write());
  }

}
