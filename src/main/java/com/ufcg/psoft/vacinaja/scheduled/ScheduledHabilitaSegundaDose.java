package com.ufcg.psoft.vacinaja.scheduled;

import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.UsuarioRepository;
import com.ufcg.psoft.vacinaja.states.EsperandoSegundaDoseState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@EnableScheduling
public class ScheduledHabilitaSegundaDose {

    private static final Logger log = LoggerFactory.getLogger(ScheduledHabilitaSegundaDose.class);
    private final long MINUTO = 1000 * 60;
    private final long DIA = 24 * MINUTO * 60;

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Scheduled(fixedDelay = DIA)
    void reportCurrentTime() {
        List<Cidadao> cidadaos = cidadaoRepository.findAll();
        for(Cidadao cidadao : cidadaos) {
            if(cidadao.getRegistroVacinacao().getEstadoVacinacao() instanceof EsperandoSegundaDoseState) {
                String email = usuarioRepository.getUsuarioByCadastroCidadao(cidadao).get().getEmail();
                cidadao.getRegistroVacinacao().getEstadoVacinacao().atualizarEstado(cidadao.getRegistroVacinacao(), email);
            }
        }
    }



}
