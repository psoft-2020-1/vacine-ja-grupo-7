package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.enums.PerfilGovernoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Funcionario;

public interface FuncionarioService {

	Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO);

	List<Cidadao> habilitarPerfilVacinacao(PerfilGovernoEnum perfilGovernoEnum);

	PerfilGovernoEnum [] listarPerfisGoverno();

	Funcionario atualizarFuncionario(FuncionarioDTO funcionarioDTO);

	void deletarFuncionario(CpfDTO cpfDTO, String token);

	Funcionario listarFuncionario(CpfDTO cpfDTO, String token);

}
