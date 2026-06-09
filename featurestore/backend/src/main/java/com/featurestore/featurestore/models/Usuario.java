package com.featurestore.featurestore.models;

public class Usuario { // featurestore.usuario
    
    // primary key = email
    // foreign key = []

    private String email;
    private String nome_usuario;    
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
