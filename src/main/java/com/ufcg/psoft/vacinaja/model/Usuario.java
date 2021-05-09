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
	
	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
		
		this.permissaoAdministrador = false;
		this.permissaoCidadao = false;
		this.permissaoFuncionario = false;
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
		boolean res = false;
		
		if (cadastroCidadao != null) {
			res = true;
		
		}
		
		return res;
	}
	
	private boolean possuiCadastroDeFuncionario() {
		boolean res = false;
		
		if (cadastroFuncionario != null) {
			res = true;
		
		}
		
		return res;
	}
}