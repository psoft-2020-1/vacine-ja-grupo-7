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
public class HabilitadoPrimeiraDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public HabilitadoPrimeiraDoseState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {

    }


    @Override
    public void vacinar(RegistroVacinacao registroVacinacao, Vacina vacina, LocalDate dataVacinacao) {
        if (vacina.getNumeroDoses() == 1) {
            registroVacinacao.setEstadoVacinacao(new VacinacaoFinalizadaState());
        } else {
            registroVacinacao.setEstadoVacinacao(new EsperandoSegundaDoseState());
        }

        registroVacinacao.setDataAgendamento(null);
        registroVacinacao.setDataVacinacaoPrimeiraDose(dataVacinacao);
        registroVacinacao.setVacina(vacina);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Estado habilitado para a primeira dose.";
    }
}
