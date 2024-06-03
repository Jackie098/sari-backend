package br.com.sari_backend.models;

import java.io.Serializable;
import java.util.UUID;

import br.com.sari_backend.models.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends ModelBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true)
  private String phone;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @Column(name = "is_blocked", nullable = false)
  private boolean isBlocked;

  @OneToOne(mappedBy = "user")
  private TicketMeals ticketMeals;

  User() {
    this.isActive = true;
    this.isBlocked = false;
    this.role = RoleEnum.ALUNO;
  }
}
