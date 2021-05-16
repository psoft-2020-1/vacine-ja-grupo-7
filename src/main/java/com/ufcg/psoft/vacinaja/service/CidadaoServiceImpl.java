package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoUpdateDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.enums.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.enums.ComorbidadeEnum;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.repository.*;
import com.ufcg.psoft.vacinaja.states.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	private static final String REGEX_VALIDATE_CPF = "(?=(?:[0-9]){11}).*";
	private static final String MENSAGEM_CONSULTA_ESTAGIO = "O seu estágio de vacinação é: ";

	@Override
	public Optional<Cidadao> buscarCidadaoPeloCpf(String cpf) {
		return cidadaoRepository.findCidadaoByCpf(cpf);
	}

	private RegistroVacinacao cadastraRegistroVacinacao(String numeroCartaoSus) {
		RegistroVacinacao registroVacinacao = new RegistroVacinacao(numeroCartaoSus);
		return registroRepository.save(registroVacinacao);
	}

    @Override
    public Cidadao cadastrarCidadao(CidadaoDTO cidadaoDTO) {
        if(cidadaoDTO == null){
            throw new CidadaoInvalidoException("ErroAtualizaCidadao: Cidadão deve conter os dados obrigatórios.");
        }
        Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoDTO.getCpf());
        if (optionalCidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão já cadastrado.");
        }
        validaCidadaoDTO(cidadaoDTO);
		Optional<RegistroVacinacao> registro = registroRepository.findById(cidadaoDTO.getNumeroCartaoSus());
		if(registro.isPresent()) {
			throw new CidadaoInvalidoException("ErroCadastroCidadão: Cartão do SUS deve ser único.");
		}
        RegistroVacinacao registroVacinacao = cadastraRegistroVacinacao(cidadaoDTO.getNumeroCartaoSus());

		Cidadao cidadaoCadastrado = cidadaoRepository.save(this.criarCidadao(cidadaoDTO, registroVacinacao));
		return cidadaoCadastrado;
	}

    @Override
    public Cidadao atualizarCidadao(CidadaoUpdateDTO cidadaoUpdateDTO) {
        if(cidadaoUpdateDTO == null){
            throw new CidadaoInvalidoException("ErroAtualizaCidadao: Cidadão deve conter os dados obrigatórios.");
        }
        Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoUpdateDTO.getCpf());
        if (!optionalCidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão não cadastrado.");
        }
        validaCidadaoUpdateDTO(cidadaoUpdateDTO);
        Optional<RegistroVacinacao> registroVacinacao = registroRepository.findById(optionalCidadao.get().getRegistroVacinacao().getNumeroCartaoSus());
        Cidadao cidadaoAtualizado =  atualizarCidadao(cidadaoUpdateDTO, registroVacinacao.get());
        return cidadaoRepository.save(cidadaoAtualizado);
    }

	@Override
	public Cidadao listarCidadao(CpfDTO cpfDTO) {
		Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
		if (!optionalCidadao.isPresent()) {
			throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
		}
		return optionalCidadao.get();
	}

	@Override
	public LocalDateTime agendarVacinacao(AgendamentoDTO agendamentoDTO) {
		this.validarAgendamentoDTO(agendamentoDTO);
		LocalDateTime data = agendamentoDTO.getData();
		String cartaoSUS = agendamentoDTO.getCartaoSUS();
		Optional<RegistroVacinacao> optionalRegistro = this.registroRepository.findById(cartaoSUS);
		if (!optionalRegistro.isPresent()) {
			throw new RegistroInvalidoException("Registro de vacinação inexistente.");
		}
		RegistroVacinacao registro = this.registroRepository.getOne(cartaoSUS);
		if (registro.getEstadoVacinacao().getClass().equals(new VacinacaoFinalizadaState().getClass())) {
			throw new RegistroInvalidoException("Vacinação já finalizada para esse cidadão.");
		}
		if (registro.getEstadoVacinacao().getClass().equals(new NaoHabilitadoState().getClass())) {
			throw new RegistroInvalidoException("Vacinação não habilitada para esse cidadão.");
		}
		if (registro.getEstadoVacinacao().getClass().equals(new EsperandoSegundaDoseState().getClass())) {
			throw new RegistroInvalidoException("Cidadão ainda não habilitado para tomar segunda dose.");
		}
		if (data.isBefore(LocalDateTime.now())) {
			throw new RegistroInvalidoException("Data de agendamento inválida.");
		}
		if (registro.getEstadoVacinacao() instanceof HabilitadoPrimeiraDoseState
				|| registro.getEstadoVacinacao() instanceof HabilitadoSegundaDoseState) {
			registro.setDataAgendamento(data);
		}

		List<RegistroVacinacao> registros = registroRepository.findAll();
		for (RegistroVacinacao registroVacinacao : registros) {
			if(!agendamentoDTO.getCartaoSUS().equals(registro.getNumeroCartaoSus())){
				if (registroVacinacao.getDataAgendamento().toLocalDate().equals(agendamentoDTO.getData().toLocalDate())) {
					if (registroVacinacao.getDataAgendamento().getHour() == agendamentoDTO.getData().getHour()) {
						throw new RegistroInvalidoException(
								"Agendamento Inválido, não pode haver agendamento com mesmo horário.");
					}
				}
			}
		}
		this.registroRepository.save(registro);
		return data;
	}

	@Override
	public List<Cidadao> listarCidadaos(String token) {
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)) {
			return cidadaoRepository.findAll();
		}
		throw new ValidacaoTokenException("ErroValidacaoToken: Token informado não tem permissão para a alteração.");
	}

	@Override
	public void deletarCidadao(CpfDTO cpfDTO, String token) {
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)) {
			Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
			if (!optionalCidadao.isPresent()) {
				throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
			}
			if(funcionarioRepository.findById(cpfDTO.getCpf()).isPresent()){
				throw new CidadaoInvalidoException("ErroDeletarCidadao: Cidadão possui cadastrao como funcionário.");
			}
			cidadaoRepository.delete(optionalCidadao.get());
			usuarioRepository.delete(usuarioRepository.getUsuarioByCadastroCidadao(optionalCidadao.get()).get());
		}
		throw new ValidacaoTokenException("ErroValidacaoToken: Token informado não tem permissão para a alteração.");
	}

	@Override
	public String consultarEstagioVacinacao(CpfDTO cpfDTO) {
		Optional<Cidadao> cidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
		if(!cidadao.isPresent()) {
			throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
		}

		return MENSAGEM_CONSULTA_ESTAGIO + cidadao.get().getRegistroVacinacao().getEstadoVacinacao() + "\n";
	}

	private Cidadao criarCidadao(CidadaoDTO cidadaoDTO, RegistroVacinacao registroVacinacao) {
		List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
		if (cidadaoDTO.getComorbidadesEnums() != null && cidadaoDTO.getComorbidadesEnums().size() != 0) {
			for (ComorbidadeEnum comorbidadeEnum : cidadaoDTO.getComorbidadesEnums()) {
				Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(comorbidadeEnum.getValue());
				if (!optionalComorbidade.isPresent()) {
					throw new CidadaoInvalidoException(
							"ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
				}
				comorbidadeList.add(optionalComorbidade.get());
			}
		}
		return new Cidadao(cidadaoDTO, comorbidadeList, registroVacinacao);
	}

	private Cidadao atualizarCidadao(CidadaoUpdateDTO cidadaoUpdateDTO, RegistroVacinacao registroVacinacao) {
		List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
		if (cidadaoUpdateDTO.getComorbidadesEnums() != null && cidadaoUpdateDTO.getComorbidadesEnums().size() != 0) {
			for (ComorbidadeEnum comorbidadeEnum : cidadaoUpdateDTO.getComorbidadesEnums()) {
				Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(comorbidadeEnum.getValue());
				if (!optionalComorbidade.isPresent()) {
					throw new CidadaoInvalidoException(
							"ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
				}
				comorbidadeList.add(optionalComorbidade.get());
			}
		}
		return new Cidadao(cidadaoUpdateDTO, comorbidadeList, registroVacinacao);
	}

    private void validaCidadaoDTO(CidadaoDTO cidadaoDTO) {
        if(!cidadaoDTO.getCpf().matches(REGEX_VALIDATE_CPF)){
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
        if((cidadaoDTO.getCpf() == null || cidadaoDTO.getCpf().equals("")) || (cidadaoDTO.getEndereco() == null || cidadaoDTO.getEndereco().equals("")) || (cidadaoDTO.getNome() == null || cidadaoDTO.getNome().equals("")) || cidadaoDTO.getDataNascimento() == null || (cidadaoDTO.getProfissao() == null || cidadaoDTO.getProfissao().equals("")) || (cidadaoDTO.getTelefone() == null || cidadaoDTO.getTelefone().equals("")) || (cidadaoDTO.getNumeroCartaoSus() == null || cidadaoDTO.getNumeroCartaoSus().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
    }

    private void validaCidadaoUpdateDTO(CidadaoUpdateDTO cidadaoUpdateDTO) {
        if((cidadaoUpdateDTO.getCpf() == null || cidadaoUpdateDTO.getCpf().equals("")) || (cidadaoUpdateDTO.getEndereco() == null || cidadaoUpdateDTO.getEndereco().equals("")) || (cidadaoUpdateDTO.getNome() == null || cidadaoUpdateDTO.getNome().equals("")) || cidadaoUpdateDTO.getDataNascimento() == null || (cidadaoUpdateDTO.getProfissao() == null || cidadaoUpdateDTO.getProfissao().equals("")) || (cidadaoUpdateDTO.getTelefone() == null || cidadaoUpdateDTO.getTelefone().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
    }

	private void validarAgendamentoDTO(AgendamentoDTO agendamentoDTO) {
    	if(agendamentoDTO.getCartaoSUS() == null || agendamentoDTO.getCartaoSUS().equals("") || agendamentoDTO.getData() == null) {
    		throw new RegistroInvalidoException("Todos os campos do agendamento devem ser preenchidos.");
    	}
    }

}
