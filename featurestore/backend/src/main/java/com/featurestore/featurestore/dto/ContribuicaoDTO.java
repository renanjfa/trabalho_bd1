package com.featurestore.featurestore.dto;

public class ContribuicaoDTO {
    
    private String nome_usuario;
    private Integer datasets_criados;
    private Integer versoes_criadas;
    private Integer total_contribuicoes;
    private float contribuicao_percentual; 

    public String getNomeUsuario() {
        return this.nome_usuario;
    }

    public void setNomeUsuario(String nome) {
        this.nome_usuario = nome;
    }

    public Integer getDatasetsCriados() {
        return this.datasets_criados;
    }

    public void setDatasetsCriados(Integer qnt) {
        this.datasets_criados = qnt;
    }

    public Integer getVersoesCriadas() {
        return this.versoes_criadas;
    }

    public void setVersoesCriadas(Integer qnt) {
        this.versoes_criadas = qnt;
    }

    public Integer getTotalContribuicoes() {
        return this.total_contribuicoes;
    }

    public void setTotalContribuicoes(Integer qnt) {
        this.total_contribuicoes = qnt;
    }

    public float getContribuicaoPercentual() {
        return this.contribuicao_percentual;
    }

    public void setContribuicaoPercentual(float contribuicao) {
        this.contribuicao_percentual = contribuicao;
    }


}
