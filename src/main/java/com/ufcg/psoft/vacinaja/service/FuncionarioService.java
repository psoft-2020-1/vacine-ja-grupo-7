package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.dto.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;

public interface FuncionarioService {

	public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO);

	public PerfilVacinacao definirPerfilVacinacao(PerfilVacinacaoDTO perfilVacinacaoDTO);

	public List<PerfilVacinacao> listarPerfilVacinacao();

	public Funcionario atualizarFuncionario(FuncionarioDTO funcionarioDTO);

	void deletarFuncionario(CpfDTO cpfDTO, String token);

	Funcionario listarFuncionario(CpfDTO cpfDTO, String token);

}
