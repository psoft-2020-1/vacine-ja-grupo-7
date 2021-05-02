package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CidadaoServiceImpl implements  CidadaoService {

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private ComorbidadeRepository comorbidadeRepository;

    private static final String REGEX_VALIDATE_CPF = "(?=(?:[0-9]){11}).*";

    @Override
    public Optional<Cidadao> buscarCidadaoPeloCpf(String cpf) {
        return cidadaoRepository.findCidadaoByCpf(cpf);
    }

    @Override
    public Cidadao cadastrarCidadao(CidadaoDTO cidadaoDTO) {
        Optional<Cidadao> optionalCidadao = cidadaoRepository.findCidadaoByCpf(cidadaoDTO.getCpf());
        if (optionalCidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroCadastroCidadão: Cidadão já cadastrado.");
        }
        Cidadao cidadaoParaCadastro = this.criarCidadao(cidadaoDTO);
        return cidadaoRepository.save(cidadaoParaCadastro);
    }

    private Cidadao criarCidadao(CidadaoDTO cidadaoDTO) {
        validaCidadaoDTO(cidadaoDTO);
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
        return cidadaoRepository.save(new Cidadao(cidadaoDTO, comorbidadeList));
    }

    private void validaCidadaoDTO(CidadaoDTO cidadaoDTO) {
        if((cidadaoDTO.getCpf() == null || cidadaoDTO.getCpf().equals("")) || (cidadaoDTO.getEndereco() == null || cidadaoDTO.getEndereco().equals("")) || (cidadaoDTO.getNome() == null || cidadaoDTO.getNome().equals("")) || cidadaoDTO.getDataNascimento() == null || (cidadaoDTO.getProfissao() == null || cidadaoDTO.getProfissao().equals("")) || (cidadaoDTO.getTelefone() == null || cidadaoDTO.getTelefone().equals("")) || (cidadaoDTO.getNumeroCartaoSus() == null || cidadaoDTO.getNumeroCartaoSus().equals(""))) {
            throw new CidadaoInvalidoException("ErroValidaCidadão: Todos os campos devem ser preenchidos.");
        }
        if(!cidadaoDTO.getCpf().matches(REGEX_VALIDATE_CPF)){
            throw new CidadaoInvalidoException("ErroValidaCidadão: Cpf inválido.");
        }
    }
}
