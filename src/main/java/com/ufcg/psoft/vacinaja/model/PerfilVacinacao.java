package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.dto.PerfilVacinacaoDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PerfilVacinacao {

    @Id
    private Long id;
    private Long idadeMinima;
    @OneToOne
    private Comorbidade comorbidade;
    private String profissao;


    public PerfilVacinacao(){

    }

    public PerfilVacinacao(PerfilVacinacaoDTO perfilVacinacaoDTO, Comorbidade comorbidade){
        this.idadeMinima = perfilVacinacaoDTO.getIdadeMinima();
        this.comorbidade = comorbidade;
        this.profissao = perfilVacinacaoDTO.getProfissao();
    }

    public void updatePerfilVacinacao(PerfilVacinacaoDTO perfilVacinacaoDTO, Comorbidade comorbidade){
        this.idadeMinima = perfilVacinacaoDTO.getIdadeMinima();
        this.comorbidade = comorbidade;
        this.profissao = perfilVacinacaoDTO.getProfissao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Long idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Comorbidade getComorbidade() {
        return comorbidade;
    }

    public void setComorbidade(Comorbidade comorbidade) {
        this.comorbidade = comorbidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
