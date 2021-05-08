package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RegistroVacinacao {

    @Id
    private String numeroCartaoSus;

    @OneToOne
    private Cidadao cidadao;

    public RegistroVacinacao() { }

    public RegistroVacinacao(Cidadao cidadao) {
        this.numeroCartaoSus = cidadao.getNumeroCartaoSus();
        this.cidadao = cidadao;
    }

    public String getNumeroCartaoSus() {
        return numeroCartaoSus;
    }

    public void setNumeroCartaoSus(String numeroCartaoSus) {
        this.numeroCartaoSus = numeroCartaoSus;
    }

    public Cidadao getCidadao() {
        return cidadao;
    }

    public void setCidadao(Cidadao cidadao) {
        this.cidadao = cidadao;
    }
}
