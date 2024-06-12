package br.com.sari_backend.models.embeddables;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMealId implements Serializable {
  private UUID userId;

  private UUID ticketMealId;
}
