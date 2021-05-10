package com.ufcg.psoft.vacinaja.dto;

import com.ufcg.psoft.vacinaja.model.Usuario;

public class CadastroDTO {

	private String email;
	private String senha;
	
	public CadastroDTO(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
	public static Usuario toUsuario(CadastroDTO cadastroDTO) {
		return new Usuario(cadastroDTO.email, cadastroDTO.senha, false, true, false);
	}
}
