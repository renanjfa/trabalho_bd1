package com.featurestore.featurestore.models;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Dataset {  // featurestore.data_set
    
    // primary key = id
    // foreign key = email_usuairo

    @NotBlank
    private Integer id;

    @NotBlank
    @Size (max = 120)
    private String nome_dataset;    
    
    private String descricao;   // (type = text)
    
    @NotBlank
    private Date data;
    
    @NotBlank
    private Time hora;
    
    @NotBlank
    @Size (max = 160)
    private String email_usuario;
    
    private List<String> fontes;    // relacao e tabela = dataset_possui_fontes

    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome_dataset;
    }

    public void setNome(String nome) {
        this.nome_dataset = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return this.hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getEmailUsuario() {
        return this.email_usuario;
    }

    public void setEmailUsuario(String email) {
        this.email_usuario = email;
    }

    public List<String> getFontes() {
        return this.fontes;
    }

    public void setFontes(List<String> fontes) {
        this.fontes = fontes;
    }
}
