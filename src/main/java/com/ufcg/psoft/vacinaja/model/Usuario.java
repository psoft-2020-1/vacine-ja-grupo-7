package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;

/**
 * Entidade que representa um usuário do sistema.
 * Um usuário pode ser administrador, cidadão e funcionário. É possível ser os três simultaneamente.
 * 
 * Para se cadastrar como funcionário, o usuário deve possuir um cadastro de cidadão.
 * Um usuário com cadastro de funcionário não ganha permissão de funcionário imediatamente.
 * 
 * @author carloshgr
 */
@Entity
public class Usuario {
	@Id
	private String email;
	private String senha;
	
	private boolean permissaoAdministrador;
	private boolean permissaoCidadao;
	private boolean permissaoFuncionario;
	
	@OneToOne
	private Cidadao cadastroCidadao;
	
	@OneToOne
	private Funcionario cadastroFuncionario;

	public Usuario() {

	}
	
	public Usuario() {
		
	}
	
	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
		
		this.permissaoAdministrador = false;
		this.permissaoCidadao = false;
		this.permissaoFuncionario = false;
	}
	
	public Usuario(String email, String senha, boolean permissaoAdministrador, boolean permissaoCidadao,
			boolean permissaoFuncionario) {
		this.email = email;
		this.senha = senha;
		this.permissaoAdministrador = permissaoAdministrador;
		this.permissaoCidadao = permissaoCidadao;
		this.permissaoFuncionario = permissaoFuncionario;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isPermissaoAdministrador() {
		return permissaoAdministrador;
	}

	public void setPermissaoAdministrador(boolean permissaoAdministrador) {
		this.permissaoAdministrador = permissaoAdministrador;
	}

	public boolean isPermissaoCidadao() {
		return permissaoCidadao;
	}

	public void setPermissaoCidadao(boolean permissaoCidadao) {
		this.permissaoCidadao = permissaoCidadao;
	}

	public boolean isPermissaoFuncionario() {
		return permissaoFuncionario;
	}

	public void setPermissaoFuncionario(boolean permissaoFuncionario) {
		this.permissaoFuncionario = permissaoFuncionario;
	}

	public String getEmail() {
		return email;
	}

	public void adicionaPermissaoAdministrador() {
		this.permissaoAdministrador = true;
	}
	
	public void adicionaCadastroCidadao(Cidadao cidadao) {
		this.cadastroCidadao = cidadao;
		
		adicionaPermissaoCidadao();
	}
	
	private void adicionaPermissaoCidadao() {
		if (!possuiCadastroDeCidadao()) {
			throw new UsuarioInvalidoException("Não há um cadastro de cidadão para esse usuário!");
		}
		
		this.permissaoCidadao = true;
	}
	
	public void adicionaCadastroFuncionario(Funcionario funcionario) {
		validaCadastroFuncionario(funcionario);
		
		this.cadastroFuncionario = funcionario;
	}
	
	public void adicionaPermissaoFuncionario() {
		if (!possuiCadastroDeFuncionario()) {
			throw new UsuarioInvalidoException("Não há um cadastro de funcionário para esse usuário!");
		}
		
		this.permissaoFuncionario = true;
	}
	
	private void validaCadastroFuncionario(Funcionario funcionario) {
		if (!possuiCadastroDeCidadao()) {
			throw new UsuarioInvalidoException("Não há um cadastro de cidadão para esse usuário!");
		} 
		
		if (!funcionario.getCpf().equals(cadastroCidadao.getCpf())) {
			throw new UsuarioInvalidoException("O cpf do funcionário é inconsistente!");
		}
	}
	
	private boolean possuiCadastroDeCidadao() {
		return cadastroCidadao != null;
	}
	
	private boolean possuiCadastroDeFuncionario() {
		return cadastroFuncionario != null;
	}
}