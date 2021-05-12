package com.ufcg.psoft.vacinaja.states;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.utils.EmailUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NaoHabilitadoState extends VacinacaoState {

    private static final String TITULO_EMAIL =
            "Você foi selecionado para ser vacinado! Agende a sua vacinação.";
    private static final String CORPO_EMAIL =
            "Olá! Sua primeira dose já está disponível para agendamento, por favor," +
                    "realize seu agendamento e esteje imunizado.";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    public NaoHabilitadoState() {

    }

    @Override
    public void atualizarEstado(RegistroVacinacao registroVacinacao, String email) {
        // TODO: IMPLEMENTAR LÓGICA COM A LISTA DE COMORBIDADES DO SISTEMA
        // TODO: IMPLEMENTAR NOTIFICAÇÃO ASSIM QUE HOUVER A TRANISÇÃO DE ESTADO
        notificar(email);
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
