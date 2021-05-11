package com.ufcg.psoft.vacinaja.dto;

public class PerfilVacinacaoDTO {


    private Long idadeMinima;
    private Long idComorbidade;
    private String profissao;


    public PerfilVacinacaoDTO(){

    }

    public Long getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Long idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Long getIdComorbidade() {
        return idComorbidade;
    }

    public void setIdComorbidade(Long idComorbidade) {
        this.idComorbidade = idComorbidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
