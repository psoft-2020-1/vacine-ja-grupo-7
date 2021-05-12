package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import com.ufcg.psoft.vacinaja.states.EsperandoSegundaDoseState;
import com.ufcg.psoft.vacinaja.states.HabilitadoPrimeiraDoseState;
import com.ufcg.psoft.vacinaja.states.NaoHabilitadoState;
import com.ufcg.psoft.vacinaja.states.VacinacaoFinalizadaState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CidadaoServiceImpl implements  CidadaoService {

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

    private void cadastraRegistroVacinacao(Cidadao cidadao) {
        RegistroVacinacao registroVacinacao = new RegistroVacinacao(cidadao);
        registroRepository.save(registroVacinacao);
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
        validaCidadaoDTOComCpf(cidadaoDTO);

        Cidadao cidadaoCadastrado = cidadaoRepository.save(this.criarCidadao(cidadaoDTO));
        cadastraRegistroVacinacao(cidadaoCadastrado);
        return cidadaoCadastrado;
    }

    @Override
    public Cidadao atualizarCidadao(CidadaoDTO cidadaoDTO) {
        if(cidadaoDTO == null){
            throw new CidadaoInvalidoException("ErroAtualizaCidadao: Cidadão deve conter os dados obrigatórios.");
        }
        Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoDTO.getCpf());
        if (!optionalCidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão não cadastrado.");
        }
        validaCidadaoDTOSemCpf(cidadaoDTO);
        Cidadao cidadaoAtualizado =  criarCidadao(cidadaoDTO);
        return cidadaoRepository.save(cidadaoAtualizado);
    }
    
    @Override
	public LocalDate agendarVacinacao(AgendamentoDTO agendamentoDTO) {
    	this.validarAgendamentoDTO(agendamentoDTO);
    	LocalDate data = agendamentoDTO.getData();
    	String cartaoSUS = agendamentoDTO.getCartaoSUS();
		Optional<RegistroVacinacao> optionalRegistro = this.registroRepository.findById(cartaoSUS);
		if(optionalRegistro.isEmpty()) {
			throw new RegistroInvalidoException("Registro de vacinação inexistente.");
		}
		RegistroVacinacao registro = this.registroRepository.getOne(cartaoSUS);
		if(registro.getEstadoVacinacao().getClass().equals(new VacinacaoFinalizadaState().getClass())) {
			throw new RegistroInvalidoException("Vacinação já finalizada para esse cidadão.");
		}
		if(registro.getEstadoVacinacao().getClass().equals(new NaoHabilitadoState().getClass())) {
			throw new RegistroInvalidoException("Vacinação não habilitada para esse cidadão.");
		}
		if(registro.getEstadoVacinacao().getClass().equals(new EsperandoSegundaDoseState().getClass())) {
			throw new RegistroInvalidoException("Cidadão ainda não habilitado para tomar segunda dose.");
		}
		if(data.isBefore(LocalDate.now())) {
			throw new RegistroInvalidoException("Data de agendamento inválida.");
		}
		if(registro.getEstadoVacinacao().getClass().equals(new HabilitadoPrimeiraDoseState().getClass())) {
			registro.setDataVacinacaoPrimeiraDose(data);
		}else {
			registro.setDataVacinacaoSegundaDose(data);
		}
		this.registroRepository.save(registro);
		return data;
	}

    private Cidadao criarCidadao(CidadaoDTO cidadaoDTO) {
        List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
        if(cidadaoDTO.getComorbidades() != null){
            for (Long idComorbidade : cidadaoDTO.getComorbidades()) {
                Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(idComorbidade);
                if(!optionalComorbidade.isPresent()){
                    throw new CidadaoInvalidoException("ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
                }
                comorbidadeList.add(optionalComorbidade.get());
            }
        }
        return new Cidadao(cidadaoDTO, comorbidadeList);
    }

    private void validaCidadaoDTOComCpf(CidadaoDTO cidadaoDTO) {
        this.validaCidadaoDTOSemCpf(cidadaoDTO);
        if(!cidadaoDTO.getCpf().matches(REGEX_VALIDATE_CPF)){
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
    }

    private void validaCidadaoDTOSemCpf(CidadaoDTO cidadaoDTO) {
        if((cidadaoDTO.getCpf() == null || cidadaoDTO.getCpf().equals("")) || (cidadaoDTO.getEndereco() == null || cidadaoDTO.getEndereco().equals("")) || (cidadaoDTO.getNome() == null || cidadaoDTO.getNome().equals("")) || cidadaoDTO.getDataNascimento() == null || (cidadaoDTO.getProfissao() == null || cidadaoDTO.getProfissao().equals("")) || (cidadaoDTO.getTelefone() == null || cidadaoDTO.getTelefone().equals("")) || (cidadaoDTO.getNumeroCartaoSus() == null || cidadaoDTO.getNumeroCartaoSus().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
    }
   
    private void validarAgendamentoDTO(AgendamentoDTO agendamentoDTO) {
    	if(agendamentoDTO.getCartaoSUS() == null || agendamentoDTO.getCartaoSUS().equals("") || agendamentoDTO.getData() == null) {
    		throw new RegistroInvalidoException("Todos os campos do agendamento devem ser preenchidos.");
    	}
    }
    
}
