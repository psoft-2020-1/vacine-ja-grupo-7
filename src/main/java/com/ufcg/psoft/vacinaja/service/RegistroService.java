package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

public interface RegistroService {
    public RegistroVacinacao vacinar(String cpfCidadao, Long vacinaId);

    public void deletarRegistro(String cpfCidadao);
}
