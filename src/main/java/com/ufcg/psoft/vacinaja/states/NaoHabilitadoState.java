package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NaoHabilitadoState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    public NaoHabilitadoState() {
        this.id = 0L;
    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao) {
        // TODO: IMPLEMENTAR LÓGICA COM A LISTA DE COMORBIDADES DO SISTEMA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
