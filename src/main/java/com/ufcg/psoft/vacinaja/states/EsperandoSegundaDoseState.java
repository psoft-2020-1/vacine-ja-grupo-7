package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class EsperandoSegundaDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public EsperandoSegundaDoseState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataVacinacao = registroVacinacao.getDataVacinacaoPrimeiraDose();

        long diferencaDias = Period.between(dataAtual, dataVacinacao).getDays();

        if(diferencaDias >= registroVacinacao.getVacina().getTempoEntreDoses()) {
            registroVacinacao.setDataVacinacaoSegundaDose(LocalDate.now());
            registroVacinacao.setEstadoVacinacao(new HabilitadoSegundaDoseState());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
