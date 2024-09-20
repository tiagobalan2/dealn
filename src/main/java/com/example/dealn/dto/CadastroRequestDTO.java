package com.example.dealn.dto;

import com.example.dealn.model.UserRole;

public record CadastroRequestDTO (String login, String nome_completo, String email, String cpf, String cnpj, String senha, Integer telefone, UserRole role){
}
