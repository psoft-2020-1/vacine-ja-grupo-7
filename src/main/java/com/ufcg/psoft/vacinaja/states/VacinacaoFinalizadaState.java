package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VacinacaoFinalizadaState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    public VacinacaoFinalizadaState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
