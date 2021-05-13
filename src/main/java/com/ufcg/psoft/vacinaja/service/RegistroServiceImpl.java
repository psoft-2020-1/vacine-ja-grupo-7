package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Override
    public RegistroVacinacao vacinar(String cpfCidadao, Long vacinaId) {
        Optional<Cidadao> cidadaoOptional = cidadaoRepository.findCidadaoByCpf(cpfCidadao);
        if(!cidadaoOptional.isPresent()) {
            throw new CidadaoInvalidoException("ErroVacinaCidadao: Cidadão não cadastrado.");
        }
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(vacinaId);
        if(!vacinaOptional.isPresent()) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Vacina não cadastrado.");
        }
        Optional<RegistroVacinacao> registroOptional = registroRepository.findById(cidadaoOptional.get().getRegistroVacinacao().getNumeroCartaoSus());
        if(!registroOptional.isPresent()) {
            throw new RegistroInvalidoException("ErroVacinaCidadao: Número de cartão do SUS presente no cidadão é inválido.");
        }

        RegistroVacinacao registroRetorno = registroOptional.get().vacinar(vacinaOptional.get());
        return registroRepository.save(registroRetorno);
    }
}
