package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.psoft.vacinaja.exceptions.AdministradorInvalidoException;
import com.ufcg.psoft.vacinaja.model.Administrador;
import com.ufcg.psoft.vacinaja.repository.AdministradorRepository;

public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private AdministradorRepository administradorRepository;
	
	
	@Override
	public Optional<Administrador> findByLogin(String loginAdmin) {
		Optional<Administrador> optionalAdministrador = administradorRepository.findById(loginAdmin);
		
		return optionalAdministrador;
	}


	@Override
	public Administrador cadastrarAdministrador(String loginAdmin) {
		this.validaAdministrador(loginAdmin);
		
		Optional<Administrador> optionalAdministrador = administradorRepository.findById(loginAdmin);
		
		if(!optionalAdministrador.isPresent()) {
			Administrador novoAdministrador = new Administrador(loginAdmin);
			return this.administradorRepository.save(novoAdministrador);
		} else {
			throw new AdministradorInvalidoException("Login já cadastrado como administrador");
		}
	}
	
	private void validaAdministrador(String login) {
		if ((login == null) || login.trim().equals("")) {
			throw new AdministradorInvalidoException("Login inválido");
		}
	}

}
