package br.com.sari_backend.services.contracts;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.User;

public interface IAuthService {
  public Map<String, String> login(User data) throws BadRequestException, NotFoundException;
}
