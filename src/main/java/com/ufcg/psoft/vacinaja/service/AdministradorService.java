package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.Usuario;

public interface AdministradorService {
	
	public Usuario cadastrarAdministrador(String loginAdmin);
	public Usuario aprovaFuncionario(String login);

}
