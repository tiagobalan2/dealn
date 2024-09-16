package com.example.dealn.controllers;

import com.example.dealn.dto.CadastroRequestDTO;
import com.example.dealn.dto.LoginRequestDTO;
import com.example.dealn.dto.LoginResponseDTO;
import com.example.dealn.infra.security.TokenService;
import com.example.dealn.model.User;
import com.example.dealn.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login") //endpoint para login de usuários
    public ResponseEntity login(@RequestBody LoginRequestDTO body) { // pede um dto como parametro que é chamado de body
        User user = this.userRepository.findByLogin(body.login()).orElseThrow(() -> new RuntimeException("Usuário nao encontrado"));
        if (passwordEncoder.matches(body.senha(), user.getSenha())) {
            String token= this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getLogin(), token));
        } return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registro") //endpoint para registro de novos usuários
    public ResponseEntity registro(@RequestBody CadastroRequestDTO body) {
        Optional<User> user = this.userRepository.findByLogin(body.login());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setLogin(body.login());
            newUser.setNome_completo(body.nome_completo());
            newUser.setEmail(body.email());
            newUser.setCpf(body.cpf());
            newUser.setTelefone(body.telefone());
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new LoginResponseDTO(newUser.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();

    }

}
