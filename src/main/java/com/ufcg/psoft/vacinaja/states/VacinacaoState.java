package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public VacinacaoState() {

    }

    public abstract void atualizarEstado(RegistroVacinacao registroVacinacao);

    public void vacinar(RegistroVacinacao registroVacinacao, Vacina vacina) {
        throw new VacinaInvalidaException("ErroVacinaCidadao: Cidadão não está habilitado a ser vacinado.");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
