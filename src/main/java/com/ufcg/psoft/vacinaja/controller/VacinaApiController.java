package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.VacinaDTO;
import com.ufcg.psoft.vacinaja.enums.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.service.JWTService;
import com.ufcg.psoft.vacinaja.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinaApiController {

	@Autowired
	private JWTService jwtService;

	@Autowired
	VacinaService vacinaService;

	/**
	 * Cadastra uma vacina a partir de: Nome único de seu fabricante; Número de
	 * telefone de 10 digitos (DDD + número); Número de doses da vacina (1 ou 2);
	 * Número de dias entre as dozes.
	 *
	 * O número de dias entre as dozes só deve ser informado caso o número de doses
	 * seja maior que 1.
	 *
	 * @param vacinaDTO DTO que possui informações referentes ao fabricante, número
	 *                  de doses da vacina e número de dias entre as dozes.
	 * @return É retornado a vacina cadastrada no banco e o status da requisição.
	 */
	@RequestMapping(value = "/vacina/", method = RequestMethod.POST)
	public ResponseEntity<?> criarVacina(@RequestBody VacinaDTO vacinaDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.ADMINISTRADOR)) {
				response = new ResponseEntity<Vacina>(vacinaService.cadastrarVacina(vacinaDTO), HttpStatus.CREATED);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (VacinaInvalidaException vie) {
			System.out.println(vie.getMessage());
			response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Pesquisa e retorna uma vacina pelo id.
	 *
	 * @param id id referente a vacina.
	 * @return retorna a vacina com o id especificado e o status da requisição.
	 */
	@RequestMapping(value = "/vacina/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVacina(@PathVariable("id") Long id, @RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.ADMINISTRADOR)) {
				response = new ResponseEntity<Vacina>(vacinaService.getVacina(id), HttpStatus.OK);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (VacinaInvalidaException vie) {
			System.out.println(vie.getMessage());
			response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Retorna todas as vacinas cadastradas.
	 *
	 * @return retorna todas as vacinas e o status da requisição.
	 */
	@RequestMapping(value = "/vacinas/", method = RequestMethod.GET)
	public ResponseEntity<?> getVacinas(@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.ADMINISTRADOR)) {
				response = new ResponseEntity<List<Vacina>>(vacinaService.getVacinas(), HttpStatus.OK);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (VacinaInvalidaException vie) {
			System.out.println(vie.getMessage());
			response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Edita uma vacina previamente cadastrada modificando os atributos: Nome único
	 * de seu fabricante; Número de telefone de 10 digitos (DDD + número); Número de
	 * doses da vacina (1 ou 2); Número de dias entre as dozes.
	 *
	 * @param id        o id referente a vacina a ser editada.
	 * @param vacinaDTO DTO que possui informações referentes ao fabricante, número
	 *                  de doses da vacina e número de dias entre as dozes.
	 * @return retorna a vacina editada e o status da requisição.
	 */
	@RequestMapping(value = "/vacina/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> editarVacina(@PathVariable("id") Long id, @RequestBody VacinaDTO vacinaDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.ADMINISTRADOR)) {
				response = new ResponseEntity<Vacina>(vacinaService.editarVacina(vacinaDTO, id), HttpStatus.OK);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (VacinaInvalidaException vie) {
			System.out.println(vie.getMessage());
			response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Deleta uma vacina a partir de um id.
	 *
	 * @param id id referente a vacina a ser deletada.
	 * @return retorna o status da requisição.
	 */
	@RequestMapping(value = "/vacina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarVacina(@PathVariable("id") Long id, @RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.ADMINISTRADOR)) {
				vacinaService.deletarVacina(id);
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (VacinaInvalidaException vie) {
			System.out.println(vie.getMessage());
			response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Retorna todas as vacinas do sistema e para os que possuem, seus lotes
	 * associados.
	 *
	 * @return retorna o toString de todos os lotes e paras as vacinas sem lote,
	 *         somente o toString da vacina.
	 */
	@RequestMapping(value = "/vacinas/lotes/", method = RequestMethod.GET)
	public ResponseEntity<?> listarVacinasLotes(@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			if (jwtService.verificaPermissao(header, PermissaoLogin.FUNCIONARIO)) {
				String vacinas = vacinaService.listarVacinas();
				response = new ResponseEntity<String>(vacinas, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>("ErroValidacaoToken: Usuario não tem permissão para a operação.",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
