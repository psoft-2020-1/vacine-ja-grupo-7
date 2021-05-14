package com.ufcg.psoft.vacinaja.model;

import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoUpdateDTO;
import com.ufcg.psoft.vacinaja.dto.PerfilVacinacaoCidadaoDTO;
import com.ufcg.psoft.vacinaja.enums.ProfissaoEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import static java.time.Period.between;

@Entity
public class Cidadao {

    @Id
    private String cpf;
    private String nome;
    private String endereco;
    @OneToOne(cascade = CascadeType.ALL)
    private RegistroVacinacao registroVacinacao;
    private LocalDate dataNascimento;
    private String telefone;
    private ProfissaoEnum profissao;
    @ManyToMany
    private List<Comorbidade> comorbidades;

    public Cidadao(){

    }

    public Cidadao (String nome, String endereco, String cpf, RegistroVacinacao registroVacinacao, LocalDate dataNascimento, String telefone, ProfissaoEnum profissao, List<Comorbidade> comorbidades){
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.registroVacinacao = registroVacinacao;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.profissao = profissao;
        this.comorbidades = comorbidades;
    }

    public Cidadao (CidadaoDTO cidadaoDTO, List<Comorbidade> comorbidades, RegistroVacinacao registroVacinacao){
        this.nome = cidadaoDTO.getNome();
        this.endereco = cidadaoDTO.getEndereco();
        this.cpf = cidadaoDTO.getCpf();
        this.dataNascimento = cidadaoDTO.getDataNascimento();
        this.telefone = cidadaoDTO.getTelefone();
        this.profissao = cidadaoDTO.getProfissao();
        this.comorbidades = comorbidades;
        this.registroVacinacao = registroVacinacao;
    }

    public Cidadao (CidadaoUpdateDTO cidadaoUpdateDTO, List<Comorbidade> comorbidades, RegistroVacinacao registroVacinacao){
        this.nome = cidadaoUpdateDTO.getNome();
        this.endereco = cidadaoUpdateDTO.getEndereco();
        this.cpf = cidadaoUpdateDTO.getCpf();
        this.dataNascimento = cidadaoUpdateDTO.getDataNascimento();
        this.telefone = cidadaoUpdateDTO.getTelefone();
        this.profissao = cidadaoUpdateDTO.getProfissao();
        this.comorbidades = comorbidades;
        this.registroVacinacao = registroVacinacao;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(List<Comorbidade> comorbidades) {
        this.comorbidades = comorbidades;
    }

    public ProfissaoEnum getProfissao() {
        return profissao;
    }

    public void setProfissao(ProfissaoEnum profissao) {
        this.profissao = profissao;
    }

    public RegistroVacinacao getRegistroVacinacao() {
        return registroVacinacao;
    }

    public void setRegistroVacinacao(RegistroVacinacao registroVacinacao) {
        this.registroVacinacao = registroVacinacao;
    }

    public PerfilVacinacaoCidadaoDTO geraPerfilVacinacao() {
        return new PerfilVacinacaoCidadaoDTO(this.getIdade(), this.comorbidades, this.profissao.getValue());
    }

    public Long getIdade() {
        return Long.valueOf(Period.between(LocalDateTime.now().toLocalDate(), this.dataNascimento).getYears());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidadao cidadao = (Cidadao) o;
        return Objects.equals(cpf, cidadao.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
