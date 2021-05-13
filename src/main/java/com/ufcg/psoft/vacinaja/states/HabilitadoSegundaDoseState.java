package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class HabilitadoSegundaDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public HabilitadoSegundaDoseState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {
        if(registroVacinacao.getDataVacinacaoSegundaDose() != null) {
            registroVacinacao.setEstadoVacinacao(new VacinacaoFinalizadaState());
        }
    }

    @Override
    public void vacinar(RegistroVacinacao registroVacinacao, Vacina vacina) {
        if(!registroVacinacao.getVacina().equals(vacina)) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: O cidadão só está habilitada a tomar a segunda" +
                    "dose do mesmo tipo de vacina da primeira dose.");
        }
        registroVacinacao.setDataVacinacaoSegundaDose(LocalDate.now());
        // TODO: DECREMENTAR NÚMERO DE VACINAS NO LOTE.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
