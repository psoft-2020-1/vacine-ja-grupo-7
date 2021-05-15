package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

public interface RegistroService {

    RegistroVacinacao vacinar(String cpfCidadao, Long vacinaId);

}
