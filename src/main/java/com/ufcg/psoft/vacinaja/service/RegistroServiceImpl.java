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
    public RegistroVacinacao vacina(String pessoaId, Long vacinaId) {
        Optional<Cidadao> cidadao = cidadaoRepository.findCidadaoByCpf(pessoaId);
        if(!cidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroVacinaCidadao: Cidadão não cadastrado.");
        }
        Optional<Vacina> vacina = vacinaRepository.findById(vacinaId);
        if(!vacina.isPresent()) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Vacina não cadastrado.");
        }
        Optional<RegistroVacinacao> registro = registroRepository.findById(cidadao.get().getNumeroCartaoSus());
        if(!registro.isPresent()) {
            throw new RegistroInvalidoException("ErroVacinaCidadao: Número de cartão do SUS presente no cidadão é inválido.");
        }

        RegistroVacinacao registroRetorno = registro.get().vacina(vacina.get());
        return registroRepository.save(registroRetorno);
    }

    @Override
    public void deletarRegistro(String pessoaId) {
        Optional<Cidadao> cidadao = cidadaoRepository.findCidadaoByCpf(pessoaId);
        if(!cidadao.isPresent()) {
            throw new CidadaoInvalidoException("ErroVacinaCidadao: CPF de cidadão não cadastrado.");
        }
        cidadaoRepository.delete(cidadao.get());
    }
}
