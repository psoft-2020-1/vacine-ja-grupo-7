package com.ufcg.psoft.vacinaja.dto;

import java.util.Date;
import java.util.List;

public class CidadaoDTO {

    private String nome;
    private String endereco;
    private String cpf;
    private String numeroCartaoSus;
    private Date dataNascimento;
    private String telefone;
    private String profissao;
    private List<Long> comorbidades;

    public CidadaoDTO (){

    }

    public CidadaoDTO (String nome, String endereco, String cpf, String numeroCartaoSus, Date dataNascimento, String telefone, String profissao, List<Long> comorbidades){
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroCartaoSus = numeroCartaoSus;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.telefone = telefone;
        this.profissao = profissao;
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

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public List<Long> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(List<Long> comorbidades) {
        this.comorbidades = comorbidades;
    }
}
