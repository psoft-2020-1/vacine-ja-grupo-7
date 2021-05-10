package com.ufcg.psoft.vacinaja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.dto.CadastroDTO;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarUsuario(@RequestBody CadastroDTO cadastroDTO) {
		Usuario usuario;
		try {
			usuario = usuarioService.cadastrarUsuario(CadastroDTO.toUsuario(cadastroDTO));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}

		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.PUT)
	public ResponseEntity<?> alterarSenhaUsuario(@PathVariable String idUsuario, @RequestBody String novaSenha,
			@RequestHeader("Authorization") String header) {
		Usuario usuario;
		try {
			usuario = usuarioService.alterarSenha(idUsuario, novaSenha, header);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValidacaoTokenException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerUsuario(@PathVariable String idUsuario,
			@RequestHeader("Authorization") String header) {
		try {
			usuarioService.removerUsuario(idUsuario, header);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValidacaoTokenException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}

}
