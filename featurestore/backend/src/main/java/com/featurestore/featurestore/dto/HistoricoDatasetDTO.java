package com.featurestore.featurestore.dto;

import java.sql.Date;

public class HistoricoDatasetDTO {
    
    private Date dia;
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

    public Date getDia() {
        return this.dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }
}
