package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoUpdateDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.enums.ComorbidadeEnum;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.model.*;
import com.ufcg.psoft.vacinaja.repository.*;
import com.ufcg.psoft.vacinaja.utils.RegistroVacinacaoComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CidadaoServiceImpl implements  CidadaoService {

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private ComorbidadeRepository comorbidadeRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private PerfilVacinacaoRepository perfilVacinacaoRepository;

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
        validaCidadaoDTOComCpf(cidadaoDTO);
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
        validaCidadaoDTOSemCpf(cidadaoUpdateDTO);
        Optional<RegistroVacinacao> registroVacinacao = registroRepository.findById(optionalCidadao.get().getRegistroVacinacao().getNumeroCartaoSus());
        Cidadao cidadaoAtualizado =  atualizarCidadao(cidadaoUpdateDTO, registroVacinacao.get());
        return cidadaoRepository.save(cidadaoAtualizado);
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

    private void validaCidadaoDTOComCpf(CidadaoDTO cidadaoDTO) {
        this.validaCidadaoDTOSemCpf(cidadaoDTO);
        if(!cidadaoDTO.getCpf().matches(REGEX_VALIDATE_CPF)){
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
    }

    private void validaCidadaoDTOSemCpf(CidadaoDTO cidadaoDTO) {
        if((cidadaoDTO.getIdade() == null || cidadaoDTO.getIdade() <= 0) || (cidadaoDTO.getCpf() == null || cidadaoDTO.getCpf().equals("")) || (cidadaoDTO.getEndereco() == null || cidadaoDTO.getEndereco().equals("")) || (cidadaoDTO.getNome() == null || cidadaoDTO.getNome().equals("")) || cidadaoDTO.getDataNascimento() == null || (cidadaoDTO.getProfissao() == null || cidadaoDTO.getProfissao().equals("")) || (cidadaoDTO.getTelefone() == null || cidadaoDTO.getTelefone().equals("")) || (cidadaoDTO.getNumeroCartaoSus() == null || cidadaoDTO.getNumeroCartaoSus().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
    }

    private void validaCidadaoDTOComCpf(CidadaoUpdateDTO cidadaoUpdateDTO) {
        this.validaCidadaoDTOSemCpf(cidadaoUpdateDTO);
        if(!cidadaoUpdateDTO.getCpf().matches(REGEX_VALIDATE_CPF)){
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
    }

    private void validaCidadaoDTOSemCpf(CidadaoUpdateDTO cidadaoUpdateDTO) {
        if((cidadaoUpdateDTO.getIdade() == null || cidadaoUpdateDTO.getIdade() <= 0) || (cidadaoUpdateDTO.getCpf() == null || cidadaoUpdateDTO.getCpf().equals("")) || (cidadaoUpdateDTO.getEndereco() == null || cidadaoUpdateDTO.getEndereco().equals("")) || (cidadaoUpdateDTO.getNome() == null || cidadaoUpdateDTO.getNome().equals("")) || cidadaoUpdateDTO.getDataNascimento() == null || (cidadaoUpdateDTO.getProfissao() == null || cidadaoUpdateDTO.getProfissao().equals("")) || (cidadaoUpdateDTO.getTelefone() == null || cidadaoUpdateDTO.getTelefone().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
    }
}
