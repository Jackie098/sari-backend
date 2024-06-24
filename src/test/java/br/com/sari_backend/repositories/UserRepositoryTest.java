package br.com.sari_backend.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sari_backend.SariBackendApplicationTests;
import br.com.sari_backend.dtos.user.CreateUserDTO;
import br.com.sari_backend.models.User;

// @DataJpaTest
// @ActiveProfiles("test")
public class UserRepositoryTest extends SariBackendApplicationTests {

  @Autowired
  UserRepository userRepository;

  // @BeforeEach
  // void setUp() {
  // System.out.println("Running setUp beforeEach test");
  // }

  @Test
  @DisplayName("Should get User successfully from DB")
  void findByEmailSuccess() {
    CreateUserDTO data = new CreateUserDTO();
    String email = "carlos@gmail.com";

    data.setName("Carlos Test");
    data.setEmail(email);
    data.setPassword("1234");
    data.setPhone("89994138240");

    this.createUser(data);

    Optional<User> result = this.userRepository.findByEmail(email);

    assertThat(result.isPresent()).isTrue();
  }

  private User createUser(CreateUserDTO data) {
    User newUser = new User();

    newUser.setName(data.getName());
    newUser.setEmail(data.getEmail());
    newUser.setPassword(data.getPassword());
    newUser.setPhone(data.getPhone());

    if (data.getRole() != null) {
      newUser.setRole(data.getRole());
    }

    newUser.setCreatedAt(Instant.now());
    newUser.setUpdatedAt(Instant.now());

    this.userRepository.save(newUser);

    return newUser;
  }
}
