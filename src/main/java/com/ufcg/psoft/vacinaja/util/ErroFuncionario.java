package com.ufcg.psoft.vacinaja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {
	
	static final String ERRO_CIDADAO_NAO_CADASTRADO = "Não há um cidadão com esse CPF cadastrado!";
	
	static final String ERRO_FUNCIONARIO_JA_CADASTRADO = "Um funcionário com o mesmo CPF já está cadastrado!";

	static final String ERRO_CPF_NULO = "O CPF não pode ser nulo!";
	
	static final String ERRO_CARGO_NULO = "O cargo não pode ser nulo!";
	
	static final String ERRO_LOCAL_DE_TRABALHO_NULO = "O local de trabalho não pode ser nulo!";
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioJaCadastrado() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_FUNCIONARIO_JA_CADASTRADO), 
				HttpStatus.CONFLICT);
	}
}
