package com.ufcg.psoft.vacinaja.dto;

import com.ufcg.psoft.vacinaja.model.Comorbidade;

import java.util.List;

public class PerfilVacinacaoCidadaoDTO {

    private Long idade;
    private List<Comorbidade> comorbidades;
    private String profissao;


    public PerfilVacinacaoCidadaoDTO(){

    }

    public PerfilVacinacaoCidadaoDTO(Long idade, List<Comorbidade> comorbidades, String profissao){
        this.idade = idade;
        this.comorbidades = comorbidades;
        this.profissao = profissao;
    }

    public Long getIdade() {
        return idade;
    }

    public void setIdade(Long idade) {
        this.idade = idade;
    }

    public List<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(List<Comorbidade> comorbidades) {
        this.comorbidades = comorbidades;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
