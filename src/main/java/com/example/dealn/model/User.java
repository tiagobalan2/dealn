package com.example.dealn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jdk.jfr.Enabled;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "O nome de usuário deve conter apenas letras, números, pontos, traços ou sublinhados")
    private String login;

    @Column(name = "nome_completo", nullable = false, length = 100)
    @Size(min = 5, message = "O nome completo deve ter no mínimo 5 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ú ']+$", message = "O nome completo deve conter apenas letras e espaços")
    private String nome_completo;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "O email deve ter no máximo 50 caracteres")
    private String email;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos")
    private String cpf;

    @Column(name = "senha", nullable = false)
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", message = "A senha deve conter pelo menos um caractere especial")
    private String senha;

    @Column(name = "telefone", nullable = false, length = 15)
    @Digits(integer = 15, fraction = 0, message = "O telefone deve conter apenas números, com no máximo 15 dígitos")
    private Integer telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    public User(String login, String senha, UserRole role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        // admin tem acesso total, user tem acesso a visualizacao mas tem que estar autenticado para isso

    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
