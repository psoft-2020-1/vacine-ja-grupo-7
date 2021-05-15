package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;

import java.util.List;

public interface VacinaService {

    Vacina cadastrarVacina(VacinaDTO vacinaDTO);

    Vacina editarVacina(VacinaDTO vacinaDTO, Long vacinaId);

    void deletarVacina(Long vacinaId);

    Vacina getVacina(Long vacinaId);

    List<Vacina> getVacinas();

    String listarVacinas();

}
