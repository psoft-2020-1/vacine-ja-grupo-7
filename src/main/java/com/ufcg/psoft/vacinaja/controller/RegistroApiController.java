package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.dto.RegistroVacinacaoDTO;
import com.ufcg.psoft.vacinaja.enums.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.service.JWTService;
import com.ufcg.psoft.vacinaja.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RegistroApiController {

	@Autowired 
	private JWTService jwtService;
	
	@Autowired
	private RegistroService registroService;

	/**
	 * Realiza a vacinação da primeira dose ou segunda dose em um cidadão.
	 *
	 * @param header token de autenticação.
	 * @param registroVacinacaoDTO contém informações referentes a vacinação deste cidadão, como
	 * armazena o identificador único do cidadão.
	 * @return O registro do cidadão atualizado após a vacinação.
	 */
	@RequestMapping(value = "/registro-vacinacao/vacinar/", method = RequestMethod.POST)
	public ResponseEntity<?> vacinar(@RequestHeader("Authorization") String header,
									 @RequestBody RegistroVacinacaoDTO registroVacinacaoDTO) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.FUNCIONARIO)) {
				RegistroVacinacao registro = registroService.vacinar(registroVacinacaoDTO);
				response = new ResponseEntity<>(registro, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (CidadaoInvalidoException | VacinaInvalidaException | RegistroInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
