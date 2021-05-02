package com.ufcg.psoft.vacinaja.dto;

public class ComorbidadeDTO {

    private String nomeComorbidade;

    public ComorbidadeDTO(){

    }

    public ComorbidadeDTO(String nomeComorbidade){
        this.nomeComorbidade = nomeComorbidade;
    }

    public String getNomeComorbidade() {
        return nomeComorbidade;
    }

    public void setNomeComorbidade(String nomeComorbidade) {
        this.nomeComorbidade = nomeComorbidade;
    }
}
