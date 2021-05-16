package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.LoteRepository;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacinaServiceImpl implements VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private LoteRepository loteRepository;

    private final static String REGEX_VALIDA_TELEFONE = "^(?=(?:[0-9]){10}).*";

    private void validarVacina(VacinaDTO vacinaDTO) {
        if(vacinaDTO == null ||
                vacinaDTO.getNomeFabricante() == null ||
                vacinaDTO.getNumeroDoses() == null ||
                vacinaDTO.getTelefoneFabricante() == null ||
                vacinaDTO.getNomeFabricante().equals("") ||
                vacinaDTO.getTelefoneFabricante().equals("")) {
            throw new VacinaInvalidaException("ErroValidarVacina: Atributos ínvalidos.");
        }
        if(vacinaDTO.getTelefoneFabricante().length() != 11 ||
                !vacinaDTO.getTelefoneFabricante().matches(REGEX_VALIDA_TELEFONE)){
            throw new VacinaInvalidaException("ErroValidarVacina: Número de telefone inválido.");
        }
        if(vacinaDTO.getNumeroDoses() <= 0 || vacinaDTO.getNumeroDoses() >= 3) {
            throw new VacinaInvalidaException("ErroValidarVacina: Número de doses inválido.");
        }
        if(vacinaDTO.getNumeroDoses() > 1 && (vacinaDTO.getTempoEntreDoses() == null || vacinaDTO.getTempoEntreDoses() <= 0)) {
            throw new VacinaInvalidaException("ErroValidarVacina: Tempo entre as doses inválido.");
        }
    }
    @Override
    public Vacina cadastrarVacina(VacinaDTO vacinaDTO) {
        validarVacina(vacinaDTO);
        Vacina vacinaParaCadastro = new Vacina(vacinaDTO);
        return vacinaRepository.save(vacinaParaCadastro);
    }

    @Override
    public Vacina getVacina(Long vacinaId) {
        Optional<Vacina> vacina = vacinaRepository.findById(vacinaId);
        if(!vacina.isPresent()) {
            throw new VacinaInvalidaException("ErroGetVacina: Vacina não existe.");
        }
        return vacina.get();
    }

    @Override
    public List<Vacina> getVacinas() {
        return vacinaRepository.findAll();
    }

    @Override
    public String listarVacinas() {
        List<Lote> lotes = this.loteRepository.findAll();
        List<Vacina> vacinas = this.vacinaRepository.findAll();
        for(Lote lote : lotes) {
            if(vacinas.contains(lote.getVacina())) {
                vacinas.remove(lote.getVacina());
            }
        }
        String retorno = "";
        for(Lote lote : lotes) {
            retorno += lote.toString() + "\n";
        }
        retorno += "Vacinas sem lote: ";
        for(Vacina vacina : vacinas) {
            retorno += vacina + "\n";
        }
        return retorno;
    }
}
