package com.ufcg.psoft.vacinaja.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.vacinaja.dto.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.enuns.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.PerfilVacinacaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import com.ufcg.psoft.vacinaja.repository.PerfilVacinacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PerfilVacinacaoRepository perfilVacinacaoRepository;

	@Autowired
	private ComorbidadeRepository comorbidadeRepository;
	
	@Autowired
	private JWTService jwtService;
	
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
	public PerfilVacinacao definirPerfilVacinacao(PerfilVacinacaoDTO perfilVacinacaoDTO) {
		this.validaPerfilVacinacaoDTO(perfilVacinacaoDTO);
		Optional<Comorbidade> comorbidadeOptional = comorbidadeRepository.findById(perfilVacinacaoDTO.getIdComorbidade());
		if(!comorbidadeOptional.isPresent()){
			throw new PerfilVacinacaoInvalidoException("ErroValidaPerfilVacinacao: Comirbidade inválida.");
		}
		List<PerfilVacinacao> perfilVacinacaoCadastrada = perfilVacinacaoRepository.findAll();
		PerfilVacinacao perfilVacinacao;
		if(perfilVacinacaoCadastrada.isEmpty()){
			perfilVacinacao = new PerfilVacinacao(perfilVacinacaoDTO, comorbidadeOptional.get());
		}else{
			perfilVacinacao = perfilVacinacaoCadastrada.get(0);
			perfilVacinacao.updatePerfilVacinacao(perfilVacinacaoDTO, comorbidadeOptional.get());
		}
		return perfilVacinacaoRepository.save(perfilVacinacao);
	}

	@Override
	public List<PerfilVacinacao> listarPerfilVacinacao() {
		return perfilVacinacaoRepository.findAll();
	}


	private void validaPerfilVacinacaoDTO(PerfilVacinacaoDTO perfilVacinacaoDTO){
		if(perfilVacinacaoDTO.getIdComorbidade() == null && perfilVacinacaoDTO.getIdadeMinima() == null && perfilVacinacaoDTO.getProfissao() == null){
			throw new PerfilVacinacaoInvalidoException("ErroValidaPerfilVacinacao: O perfil deve conter alguma restrição.");
		}if(perfilVacinacaoDTO.getIdadeMinima() < 0){
			throw new PerfilVacinacaoInvalidoException("ErroValidaPerfilVacinacao: A idade mínima tem que ser maior que 0.");
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

	@Override
	public Funcionario atualizarFuncionario(FuncionarioDTO funcionarioDTO) {
		if (funcionarioDTO == null) {
			throw new FuncionarioInvalidoException("ErroAtualizacaoFuncionario: Funcionario deve conter os dados obrigatórios.");
		}
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(funcionarioDTO.getCpfFuncionario());
		if (!optionalFuncionario.isPresent()) {
			throw new FuncionarioInvalidoException("ErroCadastroFuncionario: Funcionario não cadastrado.");
		}
		Funcionario funcionarioAtualizado = new Funcionario(funcionarioDTO);
		return funcionarioRepository.save(funcionarioAtualizado);
	}
	
	@Override
	public Funcionario listarFuncionario(CpfDTO cpfDTO, String token) {
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(cpfDTO.getCpf());
		if (!optionalFuncionario.isPresent()) {
			throw new FuncionarioInvalidoException("ErroListarFuncionario: Funcionario com esse cpf não encontrado.");
		}
		return optionalFuncionario.get();
	}

	@Override
	public void deletarFuncionario(CpfDTO cpfDTO, String token) {
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)) {
			Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(cpfDTO.getCpf());
			if (!optionalFuncionario.isPresent()) {
				throw new FuncionarioInvalidoException("ErroListarFuncionario: Funcionario com esse cpf não encontrado.");
			}
			funcionarioRepository.delete(optionalFuncionario.get());
		}
		throw new UsuarioInvalidoException("ErroListarFuncionario: Funcionario com esse cpf não encontrado.");
	}

}