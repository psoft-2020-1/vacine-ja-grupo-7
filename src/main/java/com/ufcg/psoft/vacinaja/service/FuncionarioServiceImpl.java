package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private static final String REGEX_VALIDATE_CPF = "(?=(?:[0-9]){11}).*";

	@Override
	public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
		validaFuncionarioDTO(funcionarioDTO);
		
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(funcionarioDTO.getCpfFuncionario());
		
		if (!optionalFuncionario.isPresent()) {
			Funcionario novoFuncionario = new Funcionario(funcionarioDTO);
		
			return this.funcionarioRepository.save(novoFuncionario);
		
		} else {
			throw new FuncionarioInvalidoException("ErroCadastroFuncionário: Funcionário já cadastrado.");
		}
	}
	
	private void validaFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
		if ((funcionarioDTO == null) ||
			(funcionarioDTO.getCpfFuncionario() == null) || 
			(funcionarioDTO.getCargoFuncionario() == null) || 
			(funcionarioDTO.getLocalDeTrabalhoFuncionario() == null) ||
			(funcionarioDTO.getCpfFuncionario().trim().equals("")) ||
			(funcionarioDTO.getCargoFuncionario().trim().equals("")) ||
			(funcionarioDTO.getLocalDeTrabalhoFuncionario().trim().equals(""))) {
			
			throw new FuncionarioInvalidoException("ErroValidaFuncionário: Todos os campos devem ser preenchidos.");
		}
		
		if ((!funcionarioDTO.getCpfFuncionario().matches(REGEX_VALIDATE_CPF)) ||
			(funcionarioDTO.getCpfFuncionario().length() != 11)) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
	}
}