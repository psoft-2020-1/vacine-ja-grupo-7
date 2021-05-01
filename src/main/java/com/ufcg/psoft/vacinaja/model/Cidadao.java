package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Cidadao {

    @Id
    private String cpf;
    private String nome;
    private String endereco;
    @Column(unique = true)
    private String numeroCartaoSus;
    private Date dataNascimento;
    private String telefone;
    private String profissao;
    @OneToMany
    private List<Comorbidade> comorbidades;

    public Cidadao(){

    }

    public Cidadao (String nome, String endereco, String cpf, String numeroCartaoSus, Date dataNascimento, String telefone, String profissap, List<Comorbidade> comorbidades){
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroCartaoSus = numeroCartaoSus;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.telefone = telefone;
        this.profissao = profissap;
        this.comorbidades = comorbidades;
    }

    public Cidadao (CidadaoDTO cidadaoDTO, List<Comorbidade> comorbidades){
        this.nome = cidadaoDTO.getNome();
        this.endereco = cidadaoDTO.getEndereco();
        this.cpf = cidadaoDTO.getCpf();
        this.numeroCartaoSus = cidadaoDTO.getNumeroCartaoSus();
        this.dataNascimento = cidadaoDTO.getDataNascimento();
        this.telefone = cidadaoDTO.getTelefone();
        this.telefone = cidadaoDTO.getTelefone();
        this.profissao = cidadaoDTO.getTelefone();
        this.comorbidades = comorbidades;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroCartaoSus() {
        return numeroCartaoSus;
    }

    public void setNumeroCartaoSus(String numeroCartaoSus) {
        this.numeroCartaoSus = numeroCartaoSus;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissap(String profissap) {
        this.profissao = profissao;
    }

    public List<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(List<Comorbidade> comorbidades) {
        this.comorbidades = comorbidades;
    }

}
