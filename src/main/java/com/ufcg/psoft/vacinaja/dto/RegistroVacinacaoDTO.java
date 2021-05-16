package com.ufcg.psoft.vacinaja.dto;

import java.time.LocalDate;

public class RegistroVacinacaoDTO {
    private String cpf;
    private LocalDate dataVacinacao;
    private Long idLote;
    private Long idVacina;
    private Long numeroDaDose;

    public RegistroVacinacaoDTO(String cpf, LocalDate dataVacinacao, Long idLote, Long idVacina, Long numeroDaDose) {
        this.cpf = cpf;
        this.dataVacinacao = dataVacinacao;
        this.idLote = idLote;
        this.idVacina = idVacina;
        this.numeroDaDose = numeroDaDose;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataVacinacao() {
        return dataVacinacao;
    }

    public void setDataVacinacao(LocalDate dataVacinacao) {
        this.dataVacinacao = dataVacinacao;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public Long getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(Long idVacina) {
        this.idVacina = idVacina;
    }

    public Long getNumeroDaDose() {
        return numeroDaDose;
    }

    public void setNumeroDaDose(Long numeroDaDose) {
        this.numeroDaDose = numeroDaDose;
    }
}
