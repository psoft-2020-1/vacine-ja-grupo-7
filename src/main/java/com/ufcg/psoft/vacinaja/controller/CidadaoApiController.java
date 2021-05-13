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
	 * @param cidadaoDTO Data Transfer Object do cidadao para o cadastro.
	 * @return cidadao cadastrado.
	 */
	@RequestMapping(value = "/cadastrarCidadao/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarCidadao(@RequestBody CadastroCidadaoDTO cadastroCidadaoDTO) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaDisponibilidadeEmail(cadastroCidadaoDTO.getEmailUsuario());
			Cidadao cidadaoCadastrado = cidadaoService.cadastrarCidadao(cadastroCidadaoDTO.getCidadaoDTO());
			Usuario usuario = cadastroCidadaoDTO.getUsuario();
			usuario.adicionaCadastroCidadao(cidadaoCadastrado);
			Usuario usuarioCadastrado = usuarioService.salvarUsuario(usuario);
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
     * API para atualizar um cidadão no sistema.
     *
     * @param cidadaoUpdateDTO Data Transfer Object do cidadao para a atualização.
     * @return cidadão atualizada.
     */
    @RequestMapping(value = "/cidadao/", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarCidadao(@RequestBody CidadaoUpdateDTO cidadaoUpdateDTO,
    		@RequestHeader("Authorization") String header) {
        ResponseEntity<?> response;
        try {
        	usuarioService.verificaUsuarioPermissaoCidadao(cidadaoUpdateDTO.getCpf(), header);
            Cidadao cidadaoAtualizado = cidadaoService.atualizarCidadao(cidadaoUpdateDTO);
            response =  new ResponseEntity<>(cidadaoAtualizado, HttpStatus.OK);
        } catch (CidadaoInvalidoException cie){
            response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    /**
     * API para listar os cidadões no sistema.
     *
     * @return cidadões cadastrados.
     */
    @RequestMapping(value = "/cidadao/listar/", method = RequestMethod.GET)
    public ResponseEntity<?> listarCidadaos(@RequestHeader("Authorization") String header) {
        ResponseEntity<?> response;
        try {
            List<Cidadao> cidadaoList = cidadaoService.listarCidadaos(header);
            response =  new ResponseEntity<List<Cidadao>>(cidadaoList, HttpStatus.OK);
        } catch (ValidacaoTokenException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    
    /**
     * API para listar cidadão pelo cpf no sistema.
     *
     * @return cidadão solicitado.
     */
    @RequestMapping(value = "/cidadao/listar-cidadao", method = RequestMethod.GET)
	public ResponseEntity<?> listarCidadao(@RequestBody CpfDTO cpfDTO, @RequestHeader("Authorization") String header) {
		ResponseEntity<?> response;
		try {
			usuarioService.verificaUsuarioPermissaoCidadao(cpfDTO.getCpf(), header);
			Cidadao cidadao = cidadaoService.listarCidadao(cpfDTO);
			response = new ResponseEntity<Cidadao>(cidadao, HttpStatus.OK);
		} catch (UsuarioInvalidoException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

    /**
     * API para deletar um cidadão no sistema.
     *
     * @param cpfDTO Data Transfer Object do cpf do cidadão para a exclusão do cidadão.
     */
    @RequestMapping(value = "/cidadao/deletar/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarCidadao(@RequestBody CpfDTO cpfDTO, @RequestHeader("Authorization") String header) {
        ResponseEntity<?> response;
        try {
            cidadaoService.deletarCidadao(cpfDTO, header);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidacaoTokenException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    /**
     * API para o agendamento da vacinação de um cidadão seja a primeira ou a segunda dose.
     *
     * @param agendamentoDTO Data Transfer Object de um agendamento.
     * @return a data do agendamento.
     */
    @RequestMapping(value = "/cidadao/", method = RequestMethod.GET)
    public ResponseEntity<?> agendarVacinacao(@RequestBody AgendamentoDTO agendamentoDTO) {
    	ResponseEntity<?> response;
    	try {
    		LocalDateTime dataAgendada = this.cidadaoService.agendarVacinacao(agendamentoDTO);
    		response = new ResponseEntity<LocalDateTime>(dataAgendada, HttpStatus.OK);
    	}catch (RegistroInvalidoException rie) {
    		response = new ResponseEntity<>(rie.getMessage(), HttpStatus.BAD_REQUEST);
    	}catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return response;
    }
}
