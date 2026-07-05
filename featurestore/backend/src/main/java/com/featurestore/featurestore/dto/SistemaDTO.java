package com.featurestore.featurestore.dto;

public class SistemaDTO {
    
    private Integer qnt_datasets;
    private Integer qnt_versoes;
    private Integer qnt_usuarios;

    public Integer getQntDatasets() {
        return this.qnt_datasets;
    }

    public void setQntDatasets(Integer qnt) {
        this.qnt_datasets = qnt;
    }

    public Integer getQntVersoes() {
        return this.qnt_versoes;
    }

    public void setQntVersoes(Integer qnt) {
        this.qnt_versoes = qnt;
    }

    public Integer getQntUsuarios() {
        return this.qnt_usuarios;
    }

    public void setQntUsuarios(Integer qnt) {
        this.qnt_usuarios = qnt;
    }

}
