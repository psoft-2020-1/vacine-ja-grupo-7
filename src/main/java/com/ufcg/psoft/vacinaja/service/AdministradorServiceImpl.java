package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.psoft.vacinaja.exceptions.AdministradorInvalidoException;
import com.ufcg.psoft.vacinaja.model.Administrador;
import com.ufcg.psoft.vacinaja.repository.AdministradorRepository;

public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private UsuarioRepository UsuarioRepository;


	@Override
	public Administrador cadastrarAdministrador(String loginAdmin) {
		this.validaString(loginAdmin);
		
		Optional<Usuario> optionalAdministrador = loginRepository.findById(loginAdmin);
		
		if(!optionalAdministrador.isPresent()) {
			Usuario novoAdministrador = this.UsuarioRepository.get(loginAdmin);
			return this.UsuarioRepository.save(loginAdmin);
		} else {
			throw new AdministradorInvalidoException("Login já cadastrado como administrador");
		}
	}
	
	private void validaString(String entrada) {
		if ((entrada == null) || entrada.trim().equals("")) {
			throw new AdministradorInvalidoException("Login inválido");
		}
	}


	@Override
	public void aprovaFuncionario(String login) {
		this.validaString(login);
		
		Optional<Usuario> optionalUsuario = this.UsuarioRepository.findById(login);
		if(!optionalUsuario.isPresent) {
			
		} else {
			throw new FuncionarioInvalidoException("ErroAprovaFuncionário: Funcionário não cadastrado.");
		}
		
	}

}
