package com.featurestore.featurestore.models;

import java.sql.Time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class Versao {
    
    @NotBlank
    private Integer id_versao;

    @NotBlank
    @Size (max = 160)
    private String email_usuario;

    @NotBlank
    private Integer id_dataset;

    @NotBlank
    private Date data;

    @NotBlank
    private Time hora;

    @NotBlank
    private String csv;

    @NotBlank
    @Size (max = 120)
    private String nome_versao;

    private String descricao;

    private Integer id_versao_base;


    public Integer getId() {
        return this.id_versao;
    }

    public void setId(Integer id) {
        this.id_versao = id;
    }

    public Integer getIdVersaoBase() {
        return this.id_versao_base;
    }

    public void setIdVersaoBase(Integer id) {
        this.id_versao_base = id;
    }

    public Integer getIdDataset() {
        return this.id_dataset;
    }

    public void setIdDataset(Integer id) {
        this.id_dataset = id;
    }

    public String getEmailUsuario() {
        return this.email_usuario;
    }

    public void setEmailUsuario(String email) {
        this.email_usuario = email;
    }

    public String getCSV() {
        return this.csv;
    }

    public void setCSV(String csv) {
        this.csv = csv;
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

    public String getNome() {
        return this.nome_versao;
    }

    public void setNome(String nome) {
        this.nome_versao = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
