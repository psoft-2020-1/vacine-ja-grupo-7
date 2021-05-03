package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;
import com.ufcg.psoft.vacinaja.model.Administrador;

public interface AdministradorService {
	
	public Optional<Administrador> findByLogin(String loginAdmin);
	public Administrador cadastrarAdministrador(String loginAdmin)
	public void aprovaFuncionario(String cpf);

}
