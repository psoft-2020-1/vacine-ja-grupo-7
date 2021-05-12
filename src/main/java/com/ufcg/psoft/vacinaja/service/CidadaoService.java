package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.model.Cidadao;

import java.util.Optional;

public interface CidadaoService {

    public Optional<Cidadao> buscarCidadaoPeloCpf(String cpf);

    public Cidadao cadastrarCidadao(CidadaoDTO cidadao);

    public Cidadao atualizarCidadao(CidadaoDTO cidadao);

    public Cidadao listarCidadao(CpfDTO cpfDTO, String token);

    public void deletarCidadao(CpfDTO cpfDTO, String token);

}
