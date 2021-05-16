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

import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Altera a senha do usuario cadastrado no sistema.
	 *
	 * @param header token de autenticação.
	 * @param emailUsuario email do usuario.
	 * @param novaSenha nova senha.
	 * @return O lote cadastrado e o status da requisição.
	 */
	@RequestMapping(value = "/usuario/{emailUsuario}/", method = RequestMethod.PUT)
	public ResponseEntity<?> alterarSenhaUsuario(@PathVariable String emailUsuario, @RequestBody String novaSenha,
			@RequestHeader("Authorization") String header) {
		Usuario usuario;
		try {
			usuario = usuarioService.alterarSenha(emailUsuario, novaSenha, header);
		} catch (UsuarioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValidacaoTokenException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	/**
	 * Remove o usuário do sistema
	 *
	 * @param header token de autenticação.
	 * @param emailUsuario email do usuario.
	 * @return o status da requisição.
	 */
	@RequestMapping(value = "/usuario/{emailUsuario}/", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerUsuario(@RequestHeader("Authorization") String header,
											@PathVariable String emailUsuario) {
		try {
			usuarioService.removerUsuario(emailUsuario, header);
		} catch (UsuarioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValidacaoTokenException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}

}
