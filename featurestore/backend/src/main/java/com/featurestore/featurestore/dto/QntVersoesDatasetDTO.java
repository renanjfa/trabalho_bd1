package com.featurestore.featurestore.dto;

public class QntVersoesDatasetDTO {

    private String nome_dataset;
    private String nome_usuario;
    private Integer qnt_versoes;

    public String getNomeDataset() {
        return this.nome_dataset;
    }

    public void setNomeDataset(String nome) {
        this.nome_dataset = nome;
    }

    public Integer getQntVersoes() {
        return this.qnt_versoes;
    }

    public void setQntVersoes(Integer qnt) {
        this.qnt_versoes = qnt;
    }

    public String getNomeUsuario() {
        return this.nome_usuario;
    }

    public void setNomeUsuario(String nome) {
        this.nome_usuario = nome;
    }
}
