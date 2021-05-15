package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.utils.EmailUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class EsperandoSegundaDoseState extends VacinacaoState {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private static final String TITULO_EMAIL =
            "Sua segunda dose está disponível! Agende a sua vacinação.";
    private static final String CORPO_EMAIL =
            "Olá! Sua segunda dose já está disponível para agendamento, por favor," +
            "realize seu agendamento e esteje imunizado.";

    public EsperandoSegundaDoseState() {

    }

    //TODO job para verificar se o cidadao já está com a quantidade de dias para ser notificado.
    //TODO pegar cidadaos que estão com instanceof igual esperando segunda dose e chamar o atualizar estado.
    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataVacinacao = registroVacinacao.getDataVacinacaoPrimeiraDose();
        long diferencaDias = Period.between(dataAtual, dataVacinacao).getDays();
        if(diferencaDias >= registroVacinacao.getVacina().getTempoEntreDoses()) {
            registroVacinacao.setDataVacinacaoSegundaDose(LocalDate.now());
            registroVacinacao.setEstadoVacinacao(new HabilitadoSegundaDoseState());
            notificar(email);
        }
    }

    @Override
    public void notificar(String email) {
        EmailUtils.enviarMensagem(email, TITULO_EMAIL, CORPO_EMAIL);
        System.out.println("Mensagem de SMS enviada!");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
