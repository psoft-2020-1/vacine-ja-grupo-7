package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class HabilitadoPrimeiraDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public HabilitadoPrimeiraDoseState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao) {
        if(registroVacinacao.getDataVacinacaoPrimeiraDose() != null) {
            if(registroVacinacao.getVacina().getNumeroDoses() == 1) {
                registroVacinacao.setEstadoVacinacao(new EsperandoSegundaDoseState());
            } else {
                registroVacinacao.setEstadoVacinacao(new VacinacaoFinalizadaState());
            }
        }
    }

    @Override
    public void vacina(RegistroVacinacao registroVacinacao, Vacina vacina) {
        registroVacinacao.setDataVacinacaoPrimeiraDose(LocalDate.now());
        registroVacinacao.setVacina(vacina);
        // TODO: DECREMENTAR NÚMERO DE VACINAS NO LOTE.
        atualizarEstado(registroVacinacao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
