package com.ufcg.psoft.vacinaja.dto;

import java.util.Date;
import java.util.List;

import com.ufcg.psoft.vacinaja.model.Usuario;

public class CadastroCidadaoDTO {
	private String emailUsuario;
	private String senhaUsuario;
	private String nome;
	private String endereco;
	private String cpf;
	private String numeroCartaoSus;
	private Date dataNascimento;
	private String telefone;
	private String profissao;
	private List<Long> comorbidades;

	public CadastroCidadaoDTO(String emailUsuario, String senhaUsuario, String nome, String endereco, String cpf, String numeroCartaoSus,
			Date dataNascimento, String telefone, String profissao, List<Long> comorbidades) {
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
		this.comorbidades = comorbidades;
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

	public String getEndereco() {
		return endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNumeroCartaoSus() {
		return numeroCartaoSus;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getProfissao() {
		return profissao;
	}

	public List<Long> getComorbidades() {
		return comorbidades;
	}
	
	public CidadaoDTO getCidadaoDTO() {
		return new CidadaoDTO(nome, endereco, cpf, numeroCartaoSus, dataNascimento, telefone, profissao, comorbidades);
	}
	
	public Usuario getUsuario() {
		return new Usuario(emailUsuario, senhaUsuario, false, true, false);
	}
}
