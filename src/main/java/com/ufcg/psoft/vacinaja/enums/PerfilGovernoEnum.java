package com.ufcg.psoft.vacinaja.enums;

public enum PerfilGovernoEnum {

    MEDICO_DIABETES(65L, ProfissaoEnum.MEDICO, ComorbidadeEnum.DIABETES),
    CAMINHONEIRO_ASMA(40L, ProfissaoEnum.CAMINHONEIRO, ComorbidadeEnum.ASMA ),
    IDOSO(70L, null, null),
    IDOSO_DIABETES(70L, null, ComorbidadeEnum.DIABETES),
    IDOSO_DIABETES_MEDICO(60L, ProfissaoEnum.MEDICO, ComorbidadeEnum.DIABETES);

    private Long idade;
    private ProfissaoEnum profissao;
    private ComorbidadeEnum comorbidade;

    PerfilGovernoEnum(Long idade, ProfissaoEnum profissao, ComorbidadeEnum comorbidade){
        this.idade = idade;
        this.profissao = profissao;
        this.comorbidade = comorbidade;
    }

    public Long getIdade() {
        return idade;
    }

    public ProfissaoEnum getProfissao() {
        return profissao;
    }

    public ComorbidadeEnum getComorbidade() {
        return comorbidade;
    }

}
