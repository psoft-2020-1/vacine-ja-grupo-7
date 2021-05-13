package com.ufcg.psoft.vacinaja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;
import com.ufcg.psoft.vacinaja.repository.LoteRepository;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private VacinaRepository vacinaRepository;
	
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
	
	@Override
	public String listarVacinas() {
		List<Lote> lotes = this.loteRepository.findAll();
		List<Vacina> vacinas = this.vacinaRepository.findAll();
		for(Lote lote : lotes) {
			if(vacinas.contains(lote.getVacina())) {
				vacinas.remove(lote.getVacina());
			}
		}
		String retorno = "";
		for(Lote lote : lotes) {
			retorno += lote.toString() + "\n";
		}
		retorno += "Vacinas sem lote: ";
		for(Vacina vacina : vacinas) {
			retorno += vacina + "\n";
		}
		return retorno;
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
