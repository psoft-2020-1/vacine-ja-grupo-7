package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoUpdateDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CidadaoService {

    Optional<Cidadao> buscarCidadaoPeloCpf(String cpf);

    Cidadao cadastrarCidadao(CidadaoDTO cidadaoDTO);

    Cidadao atualizarCidadao(CidadaoUpdateDTO cidadaoUpdateDTO);

    Cidadao listarCidadao(CpfDTO cpfDTO);

    List<Cidadao> listarCidadaos(String token);

    void deletarCidadao(CpfDTO cpfDTO, String token);

    String consultarEstagioVacinacao(CpfDTO cpfDTO);

	LocalDateTime agendarVacinacao(AgendamentoDTO agendamentoDTO);

}
