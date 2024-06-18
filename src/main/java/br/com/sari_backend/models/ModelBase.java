package br.com.sari_backend.models;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class ModelBase {
  @JsonProperty("created_at")
  @CreationTimestamp(source = SourceType.DB)
  private Instant createdAt;

  @JsonProperty("updated_at")
  @UpdateTimestamp(source = SourceType.DB)
  private Instant updatedAt;
}
