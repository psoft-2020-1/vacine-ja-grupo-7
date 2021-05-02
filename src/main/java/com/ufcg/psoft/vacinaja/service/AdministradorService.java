package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;
import com.ufcg.psoft.vacinaja.model.Administrador;

public interface AdministradorService {
	
	public Administrador cadastrarAdministrador(String loginAdmin);
	public Optional<Administrador> findByLogin(String loginAdmin);

}
