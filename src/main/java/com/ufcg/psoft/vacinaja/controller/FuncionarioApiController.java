package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.enums.PerfilGovernoEnum;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.dto.CpfDTO;
import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.exceptions.FuncionarioInvalidoException;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.service.FuncionarioService;
import com.ufcg.psoft.vacinaja.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FuncionarioApiController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FuncionarioService funcionarioService;

	/**
	 * Cadastra uma funcionário a partir de: Cpf do funcionário; Cargo do
	 * funcionário; Local de trabalho do funcionário.
	 *
	 * @param funcionarioDTO carrega as informações de cadastro do funcionário.
	 *
	 * @return É retornado o funcionário cadastrado no banco de dados e o status da
	 *         requisição.
	 */
	@RequestMapping(value = "/funcionario/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;

		try {
			Usuario usuario = usuarioService.getUsuarioParaFuncionario(header);
			Funcionario funcionarioCadastrado = funcionarioService.cadastrarFuncionario(funcionarioDTO);
			usuario.adicionaCadastroFuncionario(funcionarioCadastrado);
			usuarioService.salvarUsuario(usuario);
			response = new ResponseEntity<Funcionario>(funcionarioCadastrado, HttpStatus.CREATED);
		} catch (FuncionarioInvalidoException fIE) {
			response = new ResponseEntity<>(fIE.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ValidacaoTokenException | UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@RequestMapping(value = "/funcionario/", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoFuncionario(funcionarioDTO.getCpfFuncionario(), header);
			Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(funcionarioDTO);
			response = new ResponseEntity<Funcionario>(funcionarioAtualizado, HttpStatus.OK);
		} catch (FuncionarioInvalidoException cie) {
			response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/funcionario/listar-funcionario", method = RequestMethod.GET)
	public ResponseEntity<?> listarFuncionario(@RequestBody CpfDTO cpfDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoFuncionario(cpfDTO.getCpf(), header);
			Funcionario funcionario = funcionarioService.listarFuncionario(cpfDTO, header);
			response = new ResponseEntity<Funcionario>(funcionario, HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/funcionario/deletar-funcionario", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarFuncionario(@RequestBody CpfDTO cpfDTO,
			@RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			funcionarioService.deletarFuncionario(cpfDTO, header);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/funcionario/habilitar-perfil-vacinacao", method = RequestMethod.POST)
	public ResponseEntity<?> habilitarPerfilVacinacao(@RequestBody String perfil){
		ResponseEntity<?> response;
		try {
			List<Cidadao> pessoasHabilitadas = funcionarioService.habilitarPerfilVacinacao(PerfilGovernoEnum.valueOf(perfil));
			response = new ResponseEntity<List<Cidadao>>(pessoasHabilitadas, HttpStatus.CREATED);
		}catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/funcionario/listar-perfis-governo", method = RequestMethod.GET)
	public ResponseEntity<?> listarPerfisGoverno(){
		ResponseEntity<?> response;
		try {
			PerfilGovernoEnum [] perfisGoverno = funcionarioService.listarPerfisGoverno();
			response = new ResponseEntity<PerfilGovernoEnum[]>(perfisGoverno, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	/**
	 * Retorna todas as vacinas do sistema e para os que possuem, seus lotes associados.
	 * 
	 * @return retorna o toString de todos os lotes e paras as vacinas sem lote, somente o toString da vacina.
	 */
	@RequestMapping(value = "/funcionario/", method = RequestMethod.GET)
	public ResponseEntity<?> listarVacinas() {
		ResponseEntity<?> response;
		try {
			String vacinas = funcionarioService.listarVacinas();
			response = new ResponseEntity<String>(vacinas, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}