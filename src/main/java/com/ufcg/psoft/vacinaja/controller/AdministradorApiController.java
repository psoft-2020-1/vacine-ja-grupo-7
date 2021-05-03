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
			Administrador administradorCadastrado = this.administradorService.cadastrarAdministrador(login);
			
			response = new ResponseEntity<Administrador>(administradorCadastrado, HttpStatus.CREATED);
			
		}catch (AdministradorInvalidoException adminInvalido) {
			 response = new ResponseEntity<>(adminInvalido.getMessage(), HttpStatus.BAD_REQUEST);
			 
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return response;
	}
	
	@RequestMapping(value = "/Administrador/{CPF}", method = RequestMethod.POST)
	public ResponseEntity<?> aprovarFuncionario(@PathVariable ("CPF")String cpf ) {
		try {
			
		} catch ()
	}
}
