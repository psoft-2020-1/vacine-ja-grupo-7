package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.model.Cidadao;

import java.util.Optional;

public interface CidadaoService {

    public Optional<Cidadao> buscarCidadaoPeloCpf(String cpf);

    public Cidadao cadastrarCidadao(CidadaoDTO cidadao);

    public Cidadao atualizarCidadao(CidadaoDTO cidadao);

}
