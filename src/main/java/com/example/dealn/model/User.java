package com.example.dealn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jdk.jfr.Enabled;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "nome_usuario", nullable = false, unique = true, length = 50)
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "O nome de usuário deve conter apenas letras, números, pontos, traços ou sublinhados")
    private String nome_usuario;

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
}
