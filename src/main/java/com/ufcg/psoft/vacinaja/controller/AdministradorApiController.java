package com.ufcg.psoft.vacinaja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.exceptions.AdministradorInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.service.AdministradorService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdministradorApiController {
	
	@Autowired
	private AdministradorService administradorService;
	
	@RequestMapping(value = "/Administrador/{login}", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarAdministrador(@PathVariable ("login")String login ) {
		ResponseEntity<?> response;
		try {
			Usuario administradorCadastrado = this.administradorService.cadastrarAdministrador(login);
			
			response = new ResponseEntity<Usuario>(administradorCadastrado, HttpStatus.CREATED);
			
		} catch (AdministradorInvalidoException adminInvalido) {
			 response = new ResponseEntity<>(adminInvalido.getMessage(), HttpStatus.BAD_REQUEST);
			 
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return response;
	}
	
	@RequestMapping(value = "/Administrador/{funcionario}", method = RequestMethod.POST)
	public ResponseEntity<?> aprovarFuncionario(@PathVariable ("funcionario")String funcionario ) {
		ResponseEntity<?> response;
		try {
			Usuario funcionarioAprovado = this.administradorService.aprovaFuncionario(funcionario);
			response = new ResponseEntity<Usuario>(funcionarioAprovado, HttpStatus.CREATED);
		} catch (FuncionarioInvalidoException funcionarioInvalido) {
			response = new ResponseEntity<>(funcionarioInvalido.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch(Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return response;
	}
}
