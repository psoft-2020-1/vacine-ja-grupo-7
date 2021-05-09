package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;

import java.util.List;

public interface VacinaService {

    public Vacina cadastrarVacina(VacinaDTO vacinaDTO);

    public Vacina editarVacina(VacinaDTO vacinaDTO, Long vacinaId);

    public void deletarVacina(Long vacinaId);

    public Vacina getVacina(Long vacinaId);

    public List<Vacina> getVacinas();

}
