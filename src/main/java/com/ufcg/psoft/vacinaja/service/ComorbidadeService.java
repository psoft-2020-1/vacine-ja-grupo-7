package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.ComorbidadeDTO;
import com.ufcg.psoft.vacinaja.model.Comorbidade;

import java.util.List;
import java.util.Optional;

public interface ComorbidadeService {

    Optional<Comorbidade> buscarComorbidadePeloNome(String nomeComorbidade);

    Comorbidade cadastrarComorbidade(String nomeComorbidade);

    List<Comorbidade> listarComorbidades();

    void removerComorbidade(Long idComorbidade);

    Optional<Comorbidade> buscarComorbidadePeloId(Long id);

    Comorbidade atualizarComorbidade(Long idComorbidade, ComorbidadeDTO comorbidadeDTO);
}
