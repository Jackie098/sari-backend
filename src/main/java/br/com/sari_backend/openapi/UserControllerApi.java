package br.com.sari_backend.openapi;

import org.springframework.http.ResponseEntity;

import br.com.sari_backend.dtos.user.CreateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
public interface UserControllerApi {
  @Operation(summary = "Create a user", responses = {
      @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreateUserDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  public ResponseEntity<?> createUser(CreateUserDTO data);
}
