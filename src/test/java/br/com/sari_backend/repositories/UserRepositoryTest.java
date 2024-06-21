package br.com.sari_backend.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.sari_backend.dtos.user.CreateUserDTO;
import br.com.sari_backend.models.User;
import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  EntityManager entityManager;

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
    // newUser.setPassword(data.getPassword());
    // newUser.setPhone(data.getPhone());
    // newUser.setRole(data.getRole());
    // newUser.setCreatedAt(Instant.now());
    // newUser.setUpdatedAt(Instant.now());

    this.entityManager.persist(newUser);
    // this.entityManager.flush();

    return newUser;
  }
}
