package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.CadastroCidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.service.CidadaoService;
import com.ufcg.psoft.vacinaja.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoApiController {

	@Autowired
	private CidadaoService cidadaoService;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * API para cadastrar um cidadão no sistema.
	 *
	 * @param cidadaoDTO Data Transfer Object do cidadao para o cadastro.
	 * @return cidadao cadastrado.
	 */
	@RequestMapping(value = "/cidadao/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarCidadao(@RequestBody CadastroCidadaoDTO cadastroCidadaoDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaDisponibilidadeEmail(cadastroCidadaoDTO.getEmailUsuario());
			Cidadao cidadaoCadastrado = cidadaoService.cadastrarCidadao(cadastroCidadaoDTO.getCidadaoDTO());
			Usuario usuario = cadastroCidadaoDTO.getUsuario();
			usuario.adicionaCadastroCidadao(cidadaoCadastrado);
			Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
			response = new ResponseEntity<Usuario>(usuarioCadastrado, HttpStatus.CREATED);
		} catch (CidadaoInvalidoException cie) {
			response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * API para atualziar um cidadão no sistema.
	 *
	 * @param cidadaoDTO Data Transfer Object do cidadao para a atualização.
	 * @return cidadao atualizada.
	 */
	@RequestMapping(value = "/cidadao/", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarCidadao(@RequestBody CidadaoDTO cidadaoDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaPermissaoCidadao(cidadaoDTO.getCpf(), header);
			Cidadao cidadaoAtualizado = cidadaoService.atualizarCidadao(cidadaoDTO);
			response = new ResponseEntity<>(cidadaoAtualizado, HttpStatus.OK);
		} catch (CidadaoInvalidoException cie) {
			response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/cidadao/listar-cidadao", method = RequestMethod.GET)
	public ResponseEntity<?> listarCidadao(@RequestBody CpfDTO cpfDTO, @RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			Cidadao cidadao = cidadaoService.listarCidadao(cpfDTO, header);
			response = new ResponseEntity<Cidadao>(cidadao, HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/cidadao/listar-cidadao", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarCidadao(@RequestBody CpfDTO cpfDTO, @RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			cidadaoService.deletarCidadao(cpfDTO, header);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
