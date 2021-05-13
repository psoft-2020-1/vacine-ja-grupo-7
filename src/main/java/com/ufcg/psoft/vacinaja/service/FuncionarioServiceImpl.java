package com.ufcg.psoft.vacinaja.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import com.ufcg.psoft.vacinaja.comparators.ComparatorCidadao;
import com.ufcg.psoft.vacinaja.enums.PerfilGovernoEnum;
import com.ufcg.psoft.vacinaja.model.*;
import com.ufcg.psoft.vacinaja.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CidadaoRepository cidadaoRepository;

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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
	public List<Cidadao> habilitarPerfilVacinacao(PerfilGovernoEnum perfilGovernoEnum) {
		List<Cidadao> cidadaosCadastrados = cidadaoRepository.findAll();
		PriorityQueue<Cidadao> filaPrioridadeHabilitados = new PriorityQueue<>(new ComparatorCidadao(perfilGovernoEnum));
		for(Cidadao cidadao : cidadaosCadastrados) {
			if(cidadao.getRegistroVacinacao().getDataVacinacaoPrimeiraDose() == null){
				filaPrioridadeHabilitados.add(cidadao);
			}
		}

		List<Lote> loteList = loteRepository.findAll();
		Long qtdVacinasDisponiveis = 0L;
		for(Lote lote : loteList){
			qtdVacinasDisponiveis += lote.getQuantidadePessoasVacinaveis();
		}

		List<Cidadao> cidadaosHabilitados = new ArrayList<>();

		Usuario usuario;
		for(int i = 0; i < qtdVacinasDisponiveis; i++) {
			Cidadao cidadao = filaPrioridadeHabilitados.remove();
			usuario = usuarioRepository.getUsuarioByCadastroCidadao(cidadao).get();
			cidadao.getRegistroVacinacao().atualizarEstadoVacinacao(usuario.getEmail());
			cidadaosHabilitados.add(cidadao);
		}
		return cidadaosHabilitados;
	}

	@Override
	public PerfilGovernoEnum [] listarPerfisGoverno() {
		return PerfilGovernoEnum.values();
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
			retorno += lote.toString();
		}
		retorno += "Vacinas sem lote: ";
		for(Vacina vacina : vacinas) {
			retorno += vacina;
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