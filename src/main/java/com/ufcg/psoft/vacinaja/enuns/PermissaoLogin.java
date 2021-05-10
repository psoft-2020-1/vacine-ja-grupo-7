package com.ufcg.psoft.vacinaja.enuns;

public enum PermissaoLogin {
	
	CIDADAO("Cidadao"), FUNCIONARIO("Funcionario"), ADMINISTRADOR("Administrador");
	
	private String value;
	
	PermissaoLogin(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
