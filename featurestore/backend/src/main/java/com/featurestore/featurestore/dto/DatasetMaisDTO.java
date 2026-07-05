package com.featurestore.featurestore.dto;

public class DatasetMaisDTO {
    
    private String nome_dataset;
    private String nome_usuario;
    private Integer qnt_acessos;
    private Integer qnt_downloads;

    public Integer getQntAcessos() {
        return this.qnt_acessos;
    }

    public void setQntAcessos(Integer qnt) {
        this.qnt_acessos = qnt;
    }

    public Integer getQntDownloads() {
        return this.qnt_downloads;
    }

    public void setQntDownloads(Integer qnt) {
        this.qnt_downloads = qnt;
    }

    public String getNomeDataset() {
        return this.nome_dataset;
    }

    public void setNomeDataset(String nome) {
        this.nome_dataset = nome;
    }

    public String getNomeUsuario() {
        return this.nome_usuario;
    }

    public void setNomeUsuario(String nome) {
        this.nome_usuario = nome;
    }

}
