package com.featurestore.featurestore.models;

import java.sql.Date;
import java.sql.Time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AcessoDataset {    // featurestores.usuario_acessa_dataset
    
    @NotBlank
    @Size (max = 160)
    private String email_usuario;
    
    @NotBlank
    private Integer id_dataset;
    
    @NotBlank
    private Date data;
    
    @NotBlank
    private Time hora;


    public String getEmailUsuario() {
        return this.email_usuario;
    }

    public void setEmailUsuario(String email) {
        this.email_usuario = email;
    }

    public Integer getIdDataset() {
        return this.id_dataset;
    }

    public void setIdDataset(Integer id) {
        this.id_dataset = id;
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
}
