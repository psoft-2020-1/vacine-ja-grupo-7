package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.enuns.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CidadaoServiceImpl implements CidadaoService {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private CidadaoRepository cidadaoRepository;

	@Autowired
	private ComorbidadeRepository comorbidadeRepository;

	@Autowired
	private RegistroRepository registroRepository;

	private static final String REGEX_VALIDATE_CPF = "(?=(?:[0-9]){11}).*";

	@Override
	public Optional<Cidadao> buscarCidadaoPeloCpf(String cpf) {
		return cidadaoRepository.findCidadaoByCpf(cpf);
	}

	private RegistroVacinacao cadastraRegistroVacinacao(Cidadao cidadao) {
		RegistroVacinacao registroVacinacao = new RegistroVacinacao(cidadao);
		return registroRepository.save(registroVacinacao);
	}

	@Override
	public Cidadao cadastrarCidadao(CidadaoDTO cidadaoDTO) {
		if (cidadaoDTO == null) {
			throw new CidadaoInvalidoException("ErroAtualizaCidadao: Cidadão deve conter os dados obrigatórios.");
		}
		Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoDTO.getCpf());
		if (optionalCidadao.isPresent()) {
			throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão já cadastrado.");
		}
		validaCidadaoDTOComCpf(cidadaoDTO);

		Cidadao cidadaoCadastrado = cidadaoRepository.save(this.criarCidadao(cidadaoDTO));
		RegistroVacinacao registroVacinacao = cadastraRegistroVacinacao(cidadaoCadastrado);
		registroVacinacao.atualizarEstadoVacinacao();
		return cidadaoCadastrado;
	}

	private void atualizarEstadosCidadaos() {
		List<RegistroVacinacao> registroVacinacaoList = registroRepository.findAll();

	}

	@Override
	public Cidadao atualizarCidadao(CidadaoDTO cidadaoDTO) {
		if (cidadaoDTO == null) {
			throw new CidadaoInvalidoException("ErroAtualizaCidadao: Cidadão deve conter os dados obrigatórios.");
		}
		Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoDTO.getCpf());
		if (!optionalCidadao.isPresent()) {
			throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão não cadastrado.");
		}
		validaCidadaoDTOSemCpf(cidadaoDTO);
		Cidadao cidadaoAtualizado = criarCidadao(cidadaoDTO);
		return cidadaoRepository.save(cidadaoAtualizado);
	}

	@Override
	public Cidadao listarCidadao(CpfDTO cpfDTO, String token) {
		Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
		if (!optionalCidadao.isPresent()) {
			throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
		}
		return optionalCidadao.get();
	}

	@Override
	public void deletarCidadao(CpfDTO cpfDTO, String token) {
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)) {
			Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
			if (!optionalCidadao.isPresent()) {
				throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
			}
			cidadaoRepository.delete(optionalCidadao.get());
		}
		throw new UsuarioInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
	}

	private Cidadao criarCidadao(CidadaoDTO cidadaoDTO) {
		List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
		if (cidadaoDTO.getComorbidades() != null) {
			for (Long idComorbidade : cidadaoDTO.getComorbidades()) {
				Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(idComorbidade);
				if (!optionalComorbidade.isPresent()) {
					throw new CidadaoInvalidoException(
							"ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
				}
				comorbidadeList.add(optionalComorbidade.get());
			}
		}
		return new Cidadao(cidadaoDTO, comorbidadeList);
	}

	private void validaCidadaoDTOComCpf(CidadaoDTO cidadaoDTO) {
		this.validaCidadaoDTOSemCpf(cidadaoDTO);
		if (!cidadaoDTO.getCpf().matches(REGEX_VALIDATE_CPF)) {
			throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
		}
	}

	private void validaCidadaoDTOSemCpf(CidadaoDTO cidadaoDTO) {
		if ((cidadaoDTO.getCpf() == null || cidadaoDTO.getCpf().equals(""))
				|| (cidadaoDTO.getEndereco() == null || cidadaoDTO.getEndereco().equals(""))
				|| (cidadaoDTO.getNome() == null || cidadaoDTO.getNome().equals(""))
				|| cidadaoDTO.getDataNascimento() == null
				|| (cidadaoDTO.getProfissao() == null || cidadaoDTO.getProfissao().equals(""))
				|| (cidadaoDTO.getTelefone() == null || cidadaoDTO.getTelefone().equals(""))
				|| (cidadaoDTO.getNumeroCartaoSus() == null || cidadaoDTO.getNumeroCartaoSus().equals(""))) {
			throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
		}
	}
}
