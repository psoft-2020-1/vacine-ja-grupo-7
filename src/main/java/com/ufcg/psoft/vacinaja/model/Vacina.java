package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;

import javax.persistence.*;

@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String nomeFabricante;
    private String telefoneFabricante;
    private Long numeroDoses;
    private Long tempoEntreDoses;

    public Vacina() {

    }

    public Vacina(String nomeFabricante, String telefoneFabricante, Long numeroDoses) {
        this.nomeFabricante = nomeFabricante;
        this.telefoneFabricante = telefoneFabricante;
        this.numeroDoses = numeroDoses;
    }

    public Vacina(String nomeFabricante, String telefoneFabricante, Long numeroDoses, Long tempoEntreDoses) {
        this(nomeFabricante, telefoneFabricante, numeroDoses);
        this.tempoEntreDoses = tempoEntreDoses;
    }

    public Vacina(VacinaDTO vacinaDTO) {
        this(vacinaDTO.getNomeFabricante(),
             vacinaDTO.getTelefoneFabricante(),
             vacinaDTO.getNumeroDoses(),
             vacinaDTO.getTempoEntreDoses());
    }

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }

    public String getTelefoneFabricante() {
        return telefoneFabricante;
    }

    public void setTelefoneFabricante(String telefoneFabricante) {
        this.telefoneFabricante = telefoneFabricante;
    }

    public Long getNumeroDoses() {
        return numeroDoses;
    }

    public void setNumeroDoses(Long numeroDoses) {
        this.numeroDoses = numeroDoses;
    }

    public Long getTempoEntreDoses() {
        return tempoEntreDoses;
    }

    public void setTempoEntreDoses(Long tempoEntreDoses) {
        this.tempoEntreDoses = tempoEntreDoses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
