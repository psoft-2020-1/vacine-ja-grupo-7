package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

public interface RegistroService {
    public RegistroVacinacao vacina(String pessoaId, Long vacinaId);

    public void deletarRegistro(String pessoaId);
}
