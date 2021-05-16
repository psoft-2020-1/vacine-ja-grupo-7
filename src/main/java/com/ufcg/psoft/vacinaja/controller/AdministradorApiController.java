package com.ufcg.psoft.vacinaja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.service.AdministradorService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdministradorApiController {

	@Autowired
	private AdministradorService administradorService;

	/**
	 * Api para realizar a aprovação de um funcionário cadastrado.
	 * 
	 * @param emailFuncionario login do funcionário que será aprovado.
	 * @param header token de autenticação.
	 * @return o usuario que agora possui direitos de um funcionario no sistema.
	 */
	@RequestMapping(value = "/administrador/aprovar/{funcionario}/", method = RequestMethod.POST)
	public ResponseEntity<?> aprovarFuncionario(@RequestHeader("Authorization") String header,
												@PathVariable("funcionario") String emailFuncionario) {
		ResponseEntity<?> response;
		try {
			Usuario funcionarioAprovado = this.administradorService.aprovaFuncionario(emailFuncionario, header);
			response = new ResponseEntity<Usuario>(funcionarioAprovado, HttpStatus.CREATED);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (FuncionarioInvalidoException funcionarioInvalido) {
			response = new ResponseEntity<>(funcionarioInvalido.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
