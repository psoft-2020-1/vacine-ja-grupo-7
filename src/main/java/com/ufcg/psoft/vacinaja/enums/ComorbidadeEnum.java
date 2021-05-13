package com.ufcg.psoft.vacinaja.enums;

public enum ComorbidadeEnum {

    DIABETES(1L), PRESSAO_ALTA(2L), ASMA(3L);

    private Long value;

    ComorbidadeEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}
