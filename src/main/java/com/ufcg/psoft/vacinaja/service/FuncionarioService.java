package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;

public interface FuncionarioService {

	public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO);

	public Optional<Funcionario> findByCpf(String cpfFuncionario);

}
