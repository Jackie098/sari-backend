package br.com.sari_backend.services;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sari_backend.config.exceptions.BadRequestException;
import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.models.User;
import br.com.sari_backend.services.contracts.IAuthService;
import br.com.sari_backend.services.contracts.IUserService;
import br.com.sari_backend.utils.contracts.AbstractPasswordUtils;
import br.com.sari_backend.utils.contracts.AbstractTokenUtils;
import lombok.Data;

@Service
@Data
public class AuthService implements IAuthService {
  @Autowired
  private AbstractTokenUtils tokenUtils;

  @Autowired
  private AbstractPasswordUtils passwordUtils;

  @Autowired
  private IUserService userService;

  @Value("${jwt.secret}")
  private String secret;

  public Map<String, String> login(User data) throws BadRequestException, ResourceNotFoundException {
    User user = userService.getUserByEmail(data.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordUtils.checkPass(data.getPassword(), user.getPassword())) {
      throw new BadRequestException("The email or password doesn't match");
    }

    Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

    Map<String, String> token = tokenUtils.generateToken(user.getRole() + "-login", expirationDate, user, secret);

    return token;
  }
}
