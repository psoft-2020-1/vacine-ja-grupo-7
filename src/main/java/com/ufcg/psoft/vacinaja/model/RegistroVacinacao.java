package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.states.NaoHabilitadoState;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RegistroVacinacao {

    @Id
    private String numeroCartaoSus;

    @ManyToOne(cascade= CascadeType.ALL)
    private VacinacaoState estadoVacinacao;

    @Column
    private LocalDate dataVacinacaoPrimeiraDose;

    @Column
    private LocalDate dataVacinacaoSegundaDose;

    @ManyToOne
    private Vacina vacina;

    public RegistroVacinacao() { }

    public RegistroVacinacao(String numeroCartaoSus) {
        this.numeroCartaoSus = numeroCartaoSus;
        this.estadoVacinacao = new NaoHabilitadoState();
    }

    public void atualizarEstadoVacinacao() {
        this.estadoVacinacao.atualizarEstado(this);
    }

    public RegistroVacinacao vacinar(Vacina vacina) {
        estadoVacinacao.vacinar(this, vacina);
        return this;
    }

    public String getNumeroCartaoSus() {
        return numeroCartaoSus;
    }

    public void setNumeroCartaoSus(String numeroCartaoSus) {
        this.numeroCartaoSus = numeroCartaoSus;
    }

    public VacinacaoState getEstadoVacinacao() {
        return estadoVacinacao;
    }

    public void setEstadoVacinacao(VacinacaoState estadoVacinacao) {
        this.estadoVacinacao = estadoVacinacao;
    }

    public LocalDate getDataVacinacaoPrimeiraDose() {
        return dataVacinacaoPrimeiraDose;
    }

    public void setDataVacinacaoPrimeiraDose(LocalDate dataVacinacaoPrimeiraDose) {
        this.dataVacinacaoPrimeiraDose = dataVacinacaoPrimeiraDose;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public LocalDate getDataVacinacaoSegundaDose() {
        return dataVacinacaoSegundaDose;
    }

    public void setDataVacinacaoSegundaDose(LocalDate dataVacinacaoSegundaDose) {
        this.dataVacinacaoSegundaDose = dataVacinacaoSegundaDose;
    }
}

