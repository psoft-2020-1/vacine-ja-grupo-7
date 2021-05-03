package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.ComorbidadeDTO;
import com.ufcg.psoft.vacinaja.exceptions.ComorbidadeInvalidaException;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComorbidadeServiceImpl implements ComorbidadeService{

    @Autowired
    ComorbidadeRepository comorbidadeRepository;

    @Override
    public Optional<Comorbidade> buscarComorbidadePeloNome(String nomeComorbidade) {
        return comorbidadeRepository.findComorbidadeByNomeComorbidade(nomeComorbidade);
    }

    @Override
    public Comorbidade cadastrarComorbidade(String nomeComorbidade) {
        if(nomeComorbidade == null){
            throw new ComorbidadeInvalidaException("ErroCadastroComorbidade: Nome da comorbidade obrigatório.");
        }
        Optional<Comorbidade> comorbidadeOptional = buscarComorbidadePeloNome(nomeComorbidade);
        if(!comorbidadeOptional.isPresent()){
            return comorbidadeRepository.save(new Comorbidade(nomeComorbidade));
        }else {
            throw new ComorbidadeInvalidaException("ErroCadastroComorbidade: Comorbidade já cadastrada.");
        }
    }

    @Override
    public List<Comorbidade> listarComorbidades() {
        return comorbidadeRepository.findAll();
    }

    @Override
    public void removerComorbidade(Long idComorbidade) {
        if(idComorbidade == null){
            throw new ComorbidadeInvalidaException("ErroRemoverComorbidade: Id não pode ser nulo.");
        }
        Optional<Comorbidade> optionalComorbidade = this.buscarComorbidadePeloId(idComorbidade);
        if(optionalComorbidade.get() == null){
            throw new ComorbidadeInvalidaException("ErroRemoverComorbidade: Comorbidade não cadastrada.");
        }
        comorbidadeRepository.delete(optionalComorbidade.get());
    }

    @Override
    public Optional<Comorbidade> buscarComorbidadePeloId(Long id) {
        return  comorbidadeRepository.findById(id);
    }

    @Override
    public Comorbidade atualizarComorbidade(Long idComorbidade, ComorbidadeDTO comorbidadeDTO) {
        if(idComorbidade == null){
            throw new ComorbidadeInvalidaException("ErroAtualizarComorbidade: Id não pode ser nulo.");
        }
        if(comorbidadeDTO.getNomeComorbidade() == null || comorbidadeDTO.getNomeComorbidade().equals("")){
            throw new ComorbidadeInvalidaException("ErroAtualizarComorbidade: Nome da comorbidade obrigatório.");
        }
        Comorbidade optionalComorbidade = this.buscarComorbidadePeloId(idComorbidade).get();
        if(optionalComorbidade == null){
            throw new ComorbidadeInvalidaException("ErroRemoverComorbidade: Comorbidade não cadastrada.");
        }
        optionalComorbidade.setNomeComorbidade(comorbidadeDTO.getNomeComorbidade());
        return comorbidadeRepository.save(optionalComorbidade);
    }

}
