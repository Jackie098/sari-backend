package br.com.sari_backend.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import br.com.sari_backend.SariBackendApplicationTests;

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends SariBackendApplicationTests {
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
