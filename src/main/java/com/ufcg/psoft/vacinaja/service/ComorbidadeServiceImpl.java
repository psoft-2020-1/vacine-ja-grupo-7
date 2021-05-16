package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.repository.ComorbidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComorbidadeServiceImpl implements ComorbidadeService{

    @Autowired
    private ComorbidadeRepository comorbidadeRepository;

    @Override
    public List<Comorbidade> listarComorbidades() {
        return comorbidadeRepository.findAll();
    }

}
