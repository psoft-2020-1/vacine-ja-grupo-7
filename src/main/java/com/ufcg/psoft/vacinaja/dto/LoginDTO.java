package com.ufcg.psoft.vacinaja.dto;

import com.ufcg.psoft.vacinaja.model.Usuario;

public class LoginDTO {

	private String email;
	private String senha;
	private boolean permissaoCidadao;
	private boolean permissaoFuncionario;
	private boolean permissaoAdministrador;

	public LoginDTO(String email, String senha, boolean permissaoCidadao, boolean permissaoFuncionario,
			boolean permissaoAdministrador) {
		this.email = email;
		this.senha = senha;
		this.permissaoCidadao = permissaoCidadao;
		this.permissaoFuncionario = permissaoFuncionario;
		this.permissaoAdministrador = permissaoAdministrador;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public boolean isPermissaoCidadao() {
		return permissaoCidadao;
	}

	public boolean isPermissaoFuncionario() {
		return permissaoFuncionario;
	}

	public boolean isPermissaoAdministrador() {
		return permissaoAdministrador;
	}

	public Usuario getUsuario() {
		return new Usuario(email, senha, permissaoAdministrador, permissaoCidadao, permissaoFuncionario);
	}
	
}
