package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.enums.PerfilGovernoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Funcionario;

public interface FuncionarioService {

	public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO);
	public String listarVacinas();

	public List<Cidadao> habilitarPerfilVacinacao(PerfilGovernoEnum perfilGovernoEnum);

	public PerfilGovernoEnum [] listarPerfisGoverno();

}
