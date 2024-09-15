package com.example.dealn.dto;

public record LoginResponseDTO(String nome_usuario, String token) {
    // aqui vou usar nome_usuario ao inves de nome_completo pelo fato dele ser unique-true
}
