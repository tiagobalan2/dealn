package com.example.dealn.controllers;

import com.example.dealn.model.User;
import com.example.dealn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll(); // Recupera todos os usuários do banco de dados
        return ResponseEntity.ok(users); // Retorna a lista de usuários com status 200 (OK)
    }


}
