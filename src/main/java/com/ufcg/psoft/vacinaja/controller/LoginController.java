package com.ufcg.psoft.vacinaja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.dto.LoginDTO;
import com.ufcg.psoft.vacinaja.exceptions.LoginException;
import com.ufcg.psoft.vacinaja.service.JWTService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

	@Autowired
	private JWTService jwtService;

	/**
	 * Realiza o login no sistema
	 *
	 * @param loginDTO Data Transfer Object do login, contem o email e senha e a flag
	 * que indica que tipo de login está sendo realizado.
	 *
	 * @return o token que sera usado para o usuario que fez login acessar os outros
	 * endpoints do sistema.
	 */
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		try {
			return new ResponseEntity<String>(jwtService.autenticar(loginDTO.getUsuario()), HttpStatus.OK);
		} catch(LoginException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
}
