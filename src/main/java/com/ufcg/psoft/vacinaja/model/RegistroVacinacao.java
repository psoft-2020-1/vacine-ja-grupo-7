package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.states.HabilitadoPrimeiraDoseState;
import com.ufcg.psoft.vacinaja.states.HabilitadoSegundaDoseState;
import com.ufcg.psoft.vacinaja.states.NaoHabilitadoState;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RegistroVacinacao {

    @Id
    private String numeroCartaoSus;

    @OneToOne
    private Cidadao cidadao;

    @ManyToOne(cascade= CascadeType.ALL)
    private VacinacaoState estadoVacinacao;

    private LocalDate dataVacinacaoPrimeiraDose;

    private LocalDate dataVacinacaoSegundaDose;

    @ManyToOne
    private Vacina vacina;

    public RegistroVacinacao() { }

    public RegistroVacinacao(Cidadao cidadao) {
        this.numeroCartaoSus = cidadao.getNumeroCartaoSus();
        this.cidadao = cidadao;
        this.estadoVacinacao = new NaoHabilitadoState();
    }

    public void atualizarEstadoVacinacao() {
        this.estadoVacinacao.atualizarEstado(this);
    }

    public RegistroVacinacao vacina(Vacina vacina) {
        estadoVacinacao.vacina(this, vacina);
        return this;
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

