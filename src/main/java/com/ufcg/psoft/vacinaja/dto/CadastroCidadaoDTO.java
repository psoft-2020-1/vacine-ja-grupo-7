package com.ufcg.psoft.vacinaja.dto;

import java.time.LocalDate;
import java.util.List;

import com.ufcg.psoft.vacinaja.enums.ComorbidadeEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissaoEnum;
import com.ufcg.psoft.vacinaja.model.Usuario;

public class CadastroCidadaoDTO {
	private String emailUsuario;
	private String senhaUsuario;
	private String nome;
    private String endereco;
    private String cpf;
    private String numeroCartaoSus;
    private LocalDate dataNascimento;
    private String telefone;
    private ProfissaoEnum profissao;
    private List<ComorbidadeEnum> comorbidadesEnums;

	public CadastroCidadaoDTO(String emailUsuario, String senhaUsuario, String nome, String endereco, String cpf, String numeroCartaoSus, LocalDate dataNascimento, String telefone, ProfissaoEnum profissao, List<ComorbidadeEnum> comorbidadeEnums) {
		this.emailUsuario = emailUsuario;
		this.senhaUsuario = senhaUsuario;
		 this.nome = nome;
	        this.endereco = endereco;
	        this.cpf = cpf;
	        this.numeroCartaoSus = numeroCartaoSus;
	        this.dataNascimento = dataNascimento;
	        this.telefone = telefone;
	        this.telefone = telefone;
	        this.profissao = profissao;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}
	
	public String getSenhaUsuario() {
		return senhaUsuario;
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

    public ProfissaoEnum getProfissao() {
        return profissao;
    }

    public void setProfissao(ProfissaoEnum profissao) {
        this.profissao = profissao;
    }

    public List<ComorbidadeEnum> getComorbidadesEnums() {
        return comorbidadesEnums;
    }

    public void setComorbidadesEnums(List<ComorbidadeEnum> comorbidadesEnums) {
        this.comorbidadesEnums = comorbidadesEnums;
    }
	
	public CidadaoDTO getCidadaoDTO() {
		return new CidadaoDTO(nome, endereco, cpf, numeroCartaoSus, dataNascimento, telefone, profissao, comorbidadesEnums);
	}
	
	public Usuario getUsuario() {
		return new Usuario(emailUsuario, senhaUsuario, false, true, false);
	}
}
