package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;

import java.util.List;

public interface VacinaService {

    Vacina cadastrarVacina(VacinaDTO vacinaDTO);

    Vacina getVacina(Long vacinaId);

    List<Vacina> getVacinas();

    String listarVacinas();

}
