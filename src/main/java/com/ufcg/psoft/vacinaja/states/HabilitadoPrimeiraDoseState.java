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
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {
        if(registroVacinacao.getDataVacinacaoPrimeiraDose() != null) {
            if(registroVacinacao.getVacina().getNumeroDoses() == 1) {
                registroVacinacao.setEstadoVacinacao(new VacinacaoFinalizadaState());
            } else {
                registroVacinacao.setEstadoVacinacao(new EsperandoSegundaDoseState());
            }
        }
    }

    @Override
    public void vacinar(RegistroVacinacao registroVacinacao, Vacina vacina) {
        registroVacinacao.setDataVacinacaoPrimeiraDose(LocalDate.now());
        registroVacinacao.setVacina(vacina);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
