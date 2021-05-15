package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class HabilitadoSegundaDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public HabilitadoSegundaDoseState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {

    }

    @Override
    public boolean vacinar(RegistroVacinacao registroVacinacao, Vacina vacina, Lote lote) {
        if(registroVacinacao.getDataAgendamento() != null && registroVacinacao.getDataAgendamento().toLocalDate().equals(LocalDate.now()) && registroVacinacao.getDataAgendamento().getHour() == LocalDateTime.now().getHour()) {
            registroVacinacao.setEstadoVacinacao(new VacinacaoFinalizadaState());
            registroVacinacao.setDataVacinacaoSegundaDose(LocalDate.now());
            registroVacinacao.setDataAgendamento(null);
            lote.removerVacinaSegundaDose();
            
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
