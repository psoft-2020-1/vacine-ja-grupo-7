package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.states.NaoHabilitadoState;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class RegistroVacinacao {

    @Id
    private String numeroCartaoSus;

    @ManyToOne(cascade = CascadeType.ALL)
    private VacinacaoState estadoVacinacao;

    private LocalDate dataVacinacaoPrimeiraDose;

    private LocalDate dataVacinacaoSegundaDose;

    private LocalDateTime dataAgendamento;

    @ManyToOne
    private Vacina vacina;

    public RegistroVacinacao() { }

    public RegistroVacinacao(String numeroCartaoSus) {
        this.numeroCartaoSus = numeroCartaoSus;
        this.estadoVacinacao = new NaoHabilitadoState();
    }

    public void atualizarEstadoVacinacao(String email) {
        this.estadoVacinacao.atualizarEstado(this, email);
    }

    public RegistroVacinacao vacinar(Vacina vacina, String email) {
        estadoVacinacao.vacinar(this, vacina);
        estadoVacinacao.atualizarEstado(this, email);
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

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}

