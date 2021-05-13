package com.ufcg.psoft.vacinaja.utils;

import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;

import java.util.Comparator;

public class RegistroVacinacaoComparator implements Comparator<RegistroVacinacao> {

    private PerfilVacinacao perfilVacinacaoGoverno;

    public RegistroVacinacaoComparator(PerfilVacinacao perfilVacinacaoGoverno){
        this.perfilVacinacaoGoverno = perfilVacinacaoGoverno;
    }

    @Override
    public int compare(RegistroVacinacao registroCidadao1, RegistroVacinacao registroCidadao2) {
        return 0;
    }

}
