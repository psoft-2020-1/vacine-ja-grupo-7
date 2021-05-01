package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;

import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(funcionarioDTO.getCpfFuncionario());
		
		if (!optionalFuncionario.isPresent()) {
			Funcionario novoFuncionario = new Funcionario(funcionarioDTO);
		
			return this.funcionarioRepository.save(novoFuncionario);
		
		} else {
			throw new FuncionarioInvalidoException("ErroCadastroFuncionário: Funcionário já cadastrado.");
		}
	}

	@Override
	public Optional<Funcionario> findByCpf(String cpfFuncionario) {
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(cpfFuncionario);
		
		return optionalFuncionario;
	}
	
	private void validaFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
		if ((funcionarioDTO.getCpfFuncionario() == null) || 
			(funcionarioDTO.getCargoFuncionario() == null) || 
			(funcionarioDTO.getLocalDeTrabalhoFuncionario() == null) ||
			(funcionarioDTO.getCpfFuncionario().trim().equals("")) ||
			(funcionarioDTO.getCargoFuncionario().trim().equals("")) ||
			(funcionarioDTO.getLocalDeTrabalhoFuncionario().trim().equals(""))) {
			
			throw new FuncionarioInvalidoException("ErroValidaFuncionário: Todos os campos devem ser preenchidos.");
		}
	}
}