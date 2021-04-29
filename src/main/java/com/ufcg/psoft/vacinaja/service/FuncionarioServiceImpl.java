package com.ufcg.psoft.vacinaja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
		Funcionario novoFuncionario = new Funcionario(funcionarioDTO);
		
		this.funcionarioRepository.save(novoFuncionario);
	}
}
