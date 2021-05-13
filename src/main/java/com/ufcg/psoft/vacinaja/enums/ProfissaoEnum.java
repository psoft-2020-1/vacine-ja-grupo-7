package com.ufcg.psoft.vacinaja.enums;

public enum ProfissaoEnum {

    PROFESSOR ("PROFESSOR"), MEDICO("MEDICO"), CAMINHONEIRO("CAMINHONEIRO"), OUTROS("OUTROS");

    private String value;

    ProfissaoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
