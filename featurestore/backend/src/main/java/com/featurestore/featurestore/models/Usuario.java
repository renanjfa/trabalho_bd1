package com.featurestore.featurestore.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Usuario { // featurestore.usuario
    
    // primary key = email
    // foreign key = []

    @NotBlank
    @Size (max = 160)
    private String email;

    @NotBlank
    @Size (max = 160)
    private String nome_usuario;    
    
    @NotBlank
    @Size (max = 160)
    private String senha;

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome_usuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome_usuario = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
