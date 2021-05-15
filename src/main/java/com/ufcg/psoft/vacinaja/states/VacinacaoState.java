package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public VacinacaoState() {

    }

    public abstract void atualizarEstado(RegistroVacinacao registroVacinacao, String email);

    public boolean vacinar(RegistroVacinacao registroVacinacao, Vacina vacina, Lote lote) {
        throw new VacinaInvalidaException("ErroVacinaCidadao: Cidadão não está habilitado a ser vacinado.");
    }

    public void notificar(String email) throws Exception {
        throw new Exception("NotificacaoCidadao: Notificação inválida.");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
