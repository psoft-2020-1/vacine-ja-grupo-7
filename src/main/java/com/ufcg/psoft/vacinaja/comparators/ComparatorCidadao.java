package com.ufcg.psoft.vacinaja.comparators;

import com.ufcg.psoft.vacinaja.dto.PerfilVacinacaoCidadaoDTO;
import com.ufcg.psoft.vacinaja.enums.PerfilGovernoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.utils.ComorbidadeEnumToComorbidade;

import java.util.Comparator;

public class ComparatorCidadao implements Comparator<Cidadao> {

    private PerfilGovernoEnum perfilGovernoEnum;

    public ComparatorCidadao(PerfilGovernoEnum perfilGovernoEnum) {
        this.perfilGovernoEnum = perfilGovernoEnum;
    }

    @Override
    public int compare(Cidadao cidadao1, Cidadao cidadao2) {
        PerfilVacinacaoCidadaoDTO perfilCidadao1 = cidadao1.geraPerfilVacinacao();
        PerfilVacinacaoCidadaoDTO perfilCidadao2 = cidadao2.geraPerfilVacinacao();

        int scoreCidadao1 = 0;
        if(perfilCidadao1.getComorbidades().contains(ComorbidadeEnumToComorbidade.toComorbidade(this.perfilGovernoEnum.getComorbidade()))) {
            scoreCidadao1 += 1;
        }
        if(perfilCidadao1.getProfissao().equals(this.perfilGovernoEnum.getProfissao())) {
            scoreCidadao1 += 1;
        }

        int scoreCidadao2 = 0;
        if(perfilCidadao2.getComorbidades().contains(ComorbidadeEnumToComorbidade.toComorbidade(this.perfilGovernoEnum.getComorbidade()))) {
            scoreCidadao2 += 1;
        }
        if(perfilCidadao2.getProfissao().equals(this.perfilGovernoEnum.getProfissao())) {
            scoreCidadao2 += 1;
        }

        if(scoreCidadao1 != scoreCidadao2) {
            return scoreCidadao2 - scoreCidadao1;
        }
        
        return (cidadao2.getIdade().intValue()) - (cidadao1.getIdade().intValue());
    }
}
