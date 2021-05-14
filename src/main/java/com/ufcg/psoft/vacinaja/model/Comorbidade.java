package com.ufcg.psoft.vacinaja.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comorbidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String nomeComorbidade;

    public Comorbidade(){

    }

    public Comorbidade(String nomeComorbidade){
        this.nomeComorbidade = nomeComorbidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComorbidade() {
        return nomeComorbidade;
    }

    public void setNomeComorbidade(String nomeComorbidade) {
        this.nomeComorbidade = nomeComorbidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comorbidade that = (Comorbidade) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nomeComorbidade, that.nomeComorbidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeComorbidade);
    }
}
