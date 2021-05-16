package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.RegistroVacinacaoDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.LoteRepository;
import com.ufcg.psoft.vacinaja.repository.RegistroRepository;
import com.ufcg.psoft.vacinaja.repository.UsuarioRepository;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;
import com.ufcg.psoft.vacinaja.states.EsperandoSegundaDoseState;
import com.ufcg.psoft.vacinaja.states.HabilitadoPrimeiraDoseState;
import com.ufcg.psoft.vacinaja.states.HabilitadoSegundaDoseState;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    @Autowired
    private LoteRepository loteRepository;

    @Override
    public RegistroVacinacao vacinar(RegistroVacinacaoDTO registroVacinacaoDTO) {
        validaVacinacao(registroVacinacaoDTO);

        Optional<Cidadao> cidadaoOptional = cidadaoRepository.findCidadaoByCpf(registroVacinacaoDTO.getCpf());
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(registroVacinacaoDTO.getIdVacina());
        Optional<Lote> loteOptional = loteRepository.findById(registroVacinacaoDTO.getIdLote());
        RegistroVacinacao registroVacinacao = cidadaoOptional.get().getRegistroVacinacao();

        Optional<Usuario> usuario = usuarioRepository.getUsuarioByCadastroCidadao(cidadaoOptional.get());
        String email = usuario.get().getEmail();

        LocalDate dataVacinacao = registroVacinacaoDTO.getDataVacinacao();

        RegistroVacinacao registroRetorno = null;
        if(registroVacinacaoDTO.getNumeroDaDose() == 1) {
            validaVacinacaoPrimeiraDose(loteOptional.get(), registroVacinacao.getEstadoVacinacao());

            registroRetorno = registroVacinacao.vacinar(vacinaOptional.get(), email, dataVacinacao);
            loteOptional.get().removerVacinaPrimeiraDose();

        } else {
            validaVacinacaoSegundaDose(loteOptional.get(), registroVacinacao.getEstadoVacinacao());

            registroRetorno = registroVacinacao.vacinar(vacinaOptional.get(), email, dataVacinacao);
            loteOptional.get().removerVacinaSegundaDose();
        }

        loteRepository.save(loteOptional.get());
        return registroRepository.save(registroRetorno);
    }

    private void validaVacinacao(RegistroVacinacaoDTO registroVacinacaoDTO) {
        Optional<Cidadao> cidadaoOptional = cidadaoRepository.findCidadaoByCpf(registroVacinacaoDTO.getCpf());
        if(!cidadaoOptional.isPresent()) {
            throw new CidadaoInvalidoException("ErroVacinaCidadao: Cidadão não cadastrado.");
        }
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(registroVacinacaoDTO.getIdVacina());
        if(!vacinaOptional.isPresent()) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Vacina não cadastrado.");
        }
        Optional<Lote> loteOptional = loteRepository.findById(registroVacinacaoDTO.getIdLote());
        if(!loteOptional.isPresent()) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Lote não cadastrado.");
        }
        if(!loteOptional.get().getVacina().equals(vacinaOptional.get())) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Lote não corresponde a vacina a ser aplicada.");
        }
        if(registroVacinacaoDTO.getNumeroDaDose() < 1 || registroVacinacaoDTO.getNumeroDaDose() > 2) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Dose inválida.");
        }

        LocalDate dataVacinacao = registroVacinacaoDTO.getDataVacinacao();
        if(dataVacinacao.isAfter(LocalDate.now())) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: A data de vacinação não pode ser posterior ao momento atual.");
        }
    }

    private void validaVacinacaoPrimeiraDose(Lote lote, VacinacaoState vacinacaoState) {
        if(lote.getReservadasPrimeiraDose() <= 0) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Lote possui não possui doses de primeira dose.");
        }

        if(!(vacinacaoState instanceof HabilitadoPrimeiraDoseState)) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: O cidadão não está Habilitado para receber a primeira dose.");
        }
    }

    private void validaVacinacaoSegundaDose(Lote lote, VacinacaoState vacinacaoState) {
        if(lote.getReservadasSegundaDose() <= 0) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: Lote possui não possui doses de segunda dose.");
        }

        if(!(vacinacaoState instanceof HabilitadoSegundaDoseState)) {
            throw new VacinaInvalidaException("ErroVacinaCidadao: O cidadão não está Habilitado para receber a segunda dose.");
        }
    }
}
