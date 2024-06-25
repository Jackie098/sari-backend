package br.com.sari_backend.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;

import br.com.sari_backend.SariBackendApplicationTests;
import br.com.sari_backend.dtos.user.AuthDTO;

// @RunWith(SpringRunner.class)
// @WebMvcTest(AuthController.class)
public class AuthControllerTest extends SariBackendApplicationTests {
    // @Autowired
    // private TestRestTemplate restTemplate;
    @Autowired
    AuthController authController;

    // @Test
    public void whenLoginWithValidCredentials_thenReturn200AndToken() throws Exception {
        // this.getRootUrl()
        // String url = "http://localhost:8080" + "/login";
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        AuthDTO data = new AuthDTO("test-adm@gmail.com", "1234");

        ResponseEntity<?> response = authController.login(data);

        // Map<String, Object> requestBody = new HashMap<>();
        // requestBody.put("user-test-adm", "test-adm@gmail.com");
        // requestBody.put("password", "1234");

        // HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody,
        // headers);

        // ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,
        // entity, Object.class);

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
