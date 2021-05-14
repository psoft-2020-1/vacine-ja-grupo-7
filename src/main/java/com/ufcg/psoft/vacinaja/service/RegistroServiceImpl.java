package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import com.ufcg.psoft.vacinaja.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        RegistroVacinacao registroVacinacao = cidadaoOptional.get().getRegistroVacinacao();

        Optional<Usuario> usuario = usuarioRepository.getUsuarioByCadastroCidadao(cidadaoOptional.get());

        String email = usuario.get().getEmail();

        RegistroVacinacao registroRetorno = registroVacinacao.vacinar(vacinaOptional.get(), email);
        return registroRepository.save(registroRetorno);
    }
}
