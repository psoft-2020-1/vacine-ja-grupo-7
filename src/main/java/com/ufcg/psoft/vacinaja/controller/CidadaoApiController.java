package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.CadastroCidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Usuario;

import com.ufcg.psoft.vacinaja.dto.*;
import com.ufcg.psoft.vacinaja.dto.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.service.CidadaoService;
import com.ufcg.psoft.vacinaja.service.UsuarioService;

import com.ufcg.psoft.vacinaja.states.VacinacaoState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

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
	 * @param cadastroCidadaoDTO Data Transfer Object do cidadao para o cadastro.
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
			Usuario usuarioCadastrado = usuarioService.salvarUsuario(usuario);
			response = new ResponseEntity<>(usuarioCadastrado, HttpStatus.CREATED);
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
	 * API para atualizar um cidadão no sistema.
	 *
	 * @param cidadaoUpdateDTO Data Transfer Object do cidadao para a atualização.
	 * @param header token de autenticação.
	 * @return cidadão atualizada.
	 */
	@RequestMapping(value = "/cidadao/", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarCidadao(@RequestHeader("Authorization") String header,
											  @RequestBody CidadaoUpdateDTO cidadaoUpdateDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoCidadao(cidadaoUpdateDTO.getCpf(), header);
			Cidadao cidadaoAtualizado = cidadaoService.atualizarCidadao(cidadaoUpdateDTO);
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

	/**
	 * API para listar os cidadões no sistema.
	 * @param header token de autenticação.
	 * @return cidadões cadastrados.
	 */
	@RequestMapping(value = "/cidadaos/", method = RequestMethod.GET)
	public ResponseEntity<?> listarCidadaos(@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			List<Cidadao> cidadaoList = cidadaoService.listarCidadaos(header);
			response = new ResponseEntity<>(cidadaoList, HttpStatus.OK);
		} catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * API para listar cidadão pelo cpf no sistema.
	 * @param header token de autenticação.
	 * @return cidadão solicitado.
	 */
	@RequestMapping(value = "/cidadao/listar/", method = RequestMethod.POST)
	public ResponseEntity<?> listarCidadao(@RequestHeader("Authorization") String header,
										   @RequestBody CpfDTO cpfDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoCidadao(cpfDTO.getCpf(), header);
			Cidadao cidadao = cidadaoService.listarCidadao(cpfDTO);
			response = new ResponseEntity<>(cidadao, HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * API para deletar um cidadão no sistema.
	 * @param header token de autenticação.
	 * @param cpfDTO Data Transfer Object do cpf do cidadão para a exclusão do cidadão.
	 * @return  Status da requisição.
	 */
	@RequestMapping(value = "/cidadao/", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarCidadao(@RequestHeader("Authorization") String header,
											@RequestBody CpfDTO cpfDTO) {
		ResponseEntity<?> response;
		try {
			cidadaoService.deletarCidadao(cpfDTO, header);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * API para o agendamento da vacinação de um cidadão seja a primeira ou segunda dose.
	 * @param header token de autenticação.
	 * @param agendamentoDTO Data Transfer Object de um agendamento.
	 * @return a data do agendamento.
	 */
	@RequestMapping(value = "/cidadao/agendamento/", method = RequestMethod.POST)
	public ResponseEntity<?> agendarVacinacao(@RequestHeader("Authorization") String header,
											  @RequestBody AgendamentoDTO agendamentoDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoCidadaoByCartaoSUS(agendamentoDTO.getCartaoSUS(), header);
			LocalDateTime dataAgendada = this.cidadaoService.agendarVacinacao(agendamentoDTO);
			response = new ResponseEntity<>(dataAgendada, HttpStatus.OK);
		} catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (RegistroInvalidoException rie) {
			response = new ResponseEntity<>(rie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * API para a consulta do estágio vacinação de um determinado cidadão.
	 *
	 * @param cpfDTO DTO do cpf do cidadão cujo estágio de vacinação será consultado.
	 * @param header token de autenticação.
	 * @return é retornado o estágio de vacinação.
	 */
	@RequestMapping(value = "/cidadao/estagio-vacinacao/", method = RequestMethod.POST)
	public ResponseEntity<?> consultarEstagioVacinacao(@RequestHeader("Authorization") String header,
													   @RequestBody CpfDTO cpfDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoCidadao(cpfDTO.getCpf(), header);
			String estagioVacinacaoConsulta = this.cidadaoService.consultarEstagioVacinacao(cpfDTO);
			response = new ResponseEntity<>(estagioVacinacaoConsulta, HttpStatus.OK);
		} catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (CidadaoInvalidoException cie) {
			response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
