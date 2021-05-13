package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.model.Usuario;

public interface AdministradorService {
	
	public Usuario aprovaFuncionario(String emailFuncionario, String token);

}
