package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sari_backend.services.IAuthService;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    // @Autowired
    // private MockMvc mockMvc;

    // @Autowired
    // private ObjectMapper objectMapper;

    // @MockBean
    // private IAuthService authService;

    // @Test
    // public void whenLoginWithValidCredentials_thenReturn200AndToken() throws
    // Exception {
    // AuthDTO authDTO = new AuthDTO("user@example.com", "paswoord123");
    // Map<String, String> tokenObj = new HashMap<>();

    // tokenObj.put("token", "generated-jwt-token");

    // given(authService.login(any())).willReturn(tokenObj);
    // }

}
