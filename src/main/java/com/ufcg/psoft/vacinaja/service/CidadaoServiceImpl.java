package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoUpdateDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.enums.ComorbidadeEnum;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import com.ufcg.psoft.vacinaja.states.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
	public LocalDateTime agendarVacinacao(AgendamentoDTO agendamentoDTO) {
        //TODO Realizar agendamento com dia e horario e verificar choque de horarios
    	this.validarAgendamentoDTO(agendamentoDTO);
    	LocalDateTime data = agendamentoDTO.getData();
    	String cartaoSUS = agendamentoDTO.getCartaoSUS();
		Optional<RegistroVacinacao> optionalRegistro = this.registroRepository.findById(cartaoSUS);
		if(!optionalRegistro.isPresent()) {
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
		if(data.isBefore(LocalDateTime.now())) {
			throw new RegistroInvalidoException("Data de agendamento inválida.");
		}
		if(registro.getEstadoVacinacao() instanceof HabilitadoPrimeiraDoseState || registro.getEstadoVacinacao() instanceof HabilitadoSegundaDoseState) {
			registro.setDataAgendamento(data);
		}

		List<RegistroVacinacao> registros = registroRepository.findAll();
		for(RegistroVacinacao registroVacinacao : registros) {
		    if(registroVacinacao.getDataAgendamento().toLocalDate().equals(agendamentoDTO.getData().toLocalDate())){
                if(registroVacinacao.getDataAgendamento().getHour() == agendamentoDTO.getData().getHour()){
                    throw new RegistroInvalidoException("Agendamento Inválido, não pode haver agendamento com mesmo horário.");
                }
            }
        }
		this.registroRepository.save(registro);
		return data;
	}

    @Override
    public List<Cidadao> listarCidadao() {
        return cidadaoRepository.findAll();
    }

    @Override
    public void deletarCidadao(CpfDTO cpfDTO) {
        Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cpfDTO.getCpf());
        if(!optionalCidadao.isPresent()){
            throw new CidadaoInvalidoException("ErroListarCidadao: Cidadão com esse cpf não encontrado.");
        }
        cidadaoRepository.delete(optionalCidadao.get());
    }

    private Cidadao criarCidadao(CidadaoDTO cidadaoDTO, RegistroVacinacao registroVacinacao) {
        List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
        if(cidadaoDTO.getComorbidadesEnums() != null && cidadaoDTO.getComorbidadesEnums().size() != 0) {
            for (ComorbidadeEnum comorbidadeEnum : cidadaoDTO.getComorbidadesEnums()) {
                Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(comorbidadeEnum.getValue());
                if(!optionalComorbidade.isPresent()){
                    throw new CidadaoInvalidoException("ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
                }
                comorbidadeList.add(optionalComorbidade.get());
            }
        }
        return new Cidadao(cidadaoDTO, comorbidadeList, registroVacinacao);
    }

    private Cidadao atualizarCidadao(CidadaoUpdateDTO cidadaoUpdateDTO, RegistroVacinacao registroVacinacao) {
        List<Comorbidade> comorbidadeList = new ArrayList<Comorbidade>();
        if(cidadaoUpdateDTO.getComorbidadesEnums() != null && cidadaoUpdateDTO.getComorbidadesEnums().size() != 0) {
            for (ComorbidadeEnum comorbidadeEnum : cidadaoUpdateDTO.getComorbidadesEnums()) {
                Optional<Comorbidade> optionalComorbidade = comorbidadeRepository.findById(comorbidadeEnum.getValue());
                if(!optionalComorbidade.isPresent()){
                    throw new CidadaoInvalidoException("ErroCriarCidadao: Todas as comorbidades devem estar cadastradas.");
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
