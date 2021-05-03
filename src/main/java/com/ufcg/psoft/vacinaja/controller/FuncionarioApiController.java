package com.ufcg.psoft.vacinaja.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.service.FuncionarioService;
import com.ufcg.psoft.vacinaja.util.ErroFuncionario;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FuncionarioApiController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@RequestMapping(value = "/funcionario/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
		ResponseEntity<?> response;
		
		//Idealmente eu faria isso em outro lugar
		Optional<Funcionario> optionalFuncionario = funcionarioService.findByCpf(funcionarioDTO.getCpfFuncionario());
		if (optionalFuncionario.isPresent()) {
			response = ErroFuncionario.erroFuncionarioJaCadastrado();
		
		} else {
			funcionarioService.cadastrarFuncionario(funcionarioDTO);
			
			response = new ResponseEntity<>(HttpStatus.CREATED);
		}
		
		return response;
	}
}