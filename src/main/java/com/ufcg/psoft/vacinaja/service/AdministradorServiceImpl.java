package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.exceptions.AdministradorInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.repository.UsuarioRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private UsuarioRepository UsuarioRepository;


	@Override
	public Usuario cadastrarAdministrador(String loginAdmin) {
		this.validaString(loginAdmin);
		
		Optional<Usuario> optionalAdministrador = UsuarioRepository.findById(loginAdmin);
		
		if(optionalAdministrador.isPresent()) {
			Usuario novoAdministrador = UsuarioRepository.getOne(loginAdmin);
			novoAdministrador.adicionaPermissaoAdministrador();
			return this.UsuarioRepository.save(novoAdministrador);
		} else {
			throw new AdministradorInvalidoException("Login inexistente");
		}
	}
	
	private void validaString(String entrada) {
		if ((entrada == null) || entrada.trim().equals("")) {
			throw new AdministradorInvalidoException("Login inválido");
		}
	}


	@Override
	public Usuario aprovaFuncionario(String login) {
		this.validaString(login);
		
		Optional<Usuario> optionalUsuario = this.UsuarioRepository.findById(login);
		if(optionalUsuario.isPresent()) {
			Usuario novoFuncionario = UsuarioRepository.getOne(login);
			novoFuncionario.adicionaPermissaoFuncionario();
			return this.UsuarioRepository.save(novoFuncionario);
		} else {
			throw new FuncionarioInvalidoException("ErroAprovaFuncionário: Usuário não cadastrado.");
		}
		
	}

}
