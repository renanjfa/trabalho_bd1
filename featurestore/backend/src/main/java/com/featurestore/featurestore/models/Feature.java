package com.featurestore.featurestore.models;


public class Feature {  // featurestore.feature
    
    private Integer id_feature;    
    private String nome_feature;   
    private String tipo;  
    private String descricao;   // (type = text)


    public Integer getId() {
        return this.id_feature;
    }

    public void setId(Integer id) {
        this.id_feature = id;
    }

    public String getNome() {
        return this.nome_feature;
    }

    public void setNome(String nome) {
        this.nome_feature = nome;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
