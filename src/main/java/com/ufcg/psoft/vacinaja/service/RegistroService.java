package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.dto.RegistroVacinacaoDTO;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

public interface RegistroService {

    RegistroVacinacao vacinar(RegistroVacinacaoDTO registroVacinacaoDTO);

}
