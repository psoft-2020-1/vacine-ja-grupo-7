package com.ufcg.psoft.vacinaja.dto;

public class VacinaDTO {
    private String nomeFabricante;
    private String telefoneFabricante;
    private Long numeroDoses;
    private Long tempoEntreDoses;

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public String getTelefoneFabricante() {
        return telefoneFabricante;
    }

    public Long getNumeroDoses() {
        return numeroDoses;
    }

    public Long getTempoEntreDoses() {
        return tempoEntreDoses;
    }
}
