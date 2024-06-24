package br.com.sari_backend.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.sari_backend.models.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends Base implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

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

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private List<TicketMeals> ticketMeals;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
  private List<BookMeal> bookMeals;

  public User() {
    this.isActive = true;
    this.isBlocked = false;
    this.role = RoleEnum.ALUNO;
  }

  public User(String name, String password, String phone, RoleEnum role) {
    this.name = name;
    this.password = password;
    this.phone = phone;

    this.isActive = true;
    this.isBlocked = false;

    if (role != null) {
      this.role = role;
    } else {
      this.role = RoleEnum.ALUNO;
    }
  }
}
