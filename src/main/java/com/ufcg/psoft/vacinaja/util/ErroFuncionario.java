package com.ufcg.psoft.vacinaja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {
	
	static final String ERRO_CIDADAO_NAO_CADASTRADO = "Não há um cidadão com esse CPF cadastrado!";
	
	static final String ERRO_FUNCIONARIO_JA_CADASTRADO = "Um funcionário com o mesmo CPF já está cadastrado!";

	static final String ERRO_CPF_NULO = "O CPF não pode ser nulo!";
	
	static final String ERRO_CARGO_NULO = "O cargo não pode ser nulo!";
	
	static final String ERRO_LOCAL_DE_TRABALHO_NULO = "O local de trabalho não pode ser nulo!";
	
	static final String ERRO_CPF_VAZIO = "O CPF não pode ser vazio!";
	
	static final String ERRO_CARGO_VAZIO = "O cargo não pode ser vazio!";
	
	static final String ERRO_LOCAL_DE_TRABALHO_VAZIO = "O local de trabalho não pode ser vazio!";
	
	public static ResponseEntity<CustomErrorType> erroCidadaoNaoCadastrado() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_CIDADAO_NAO_CADASTRADO), 
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioJaCadastrado() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_FUNCIONARIO_JA_CADASTRADO), 
				HttpStatus.CONFLICT);
	}
	
	public static ResponseEntity<CustomErrorType> erroCpfFuncionarioNulo() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_CPF_NULO), 
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroCargoFuncionarioNulo() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_CARGO_NULO), 
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroLocalDeTrabalhoFuncionarioNulo() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_LOCAL_DE_TRABALHO_NULO), 
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroCpfFuncionarioVazio() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_CPF_VAZIO), 
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroCargoFuncionarioVazio() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_CARGO_VAZIO), 
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroLocalDeTrabalhoFuncionarioVazio() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ERRO_LOCAL_DE_TRABALHO_VAZIO), 
				HttpStatus.BAD_REQUEST);
	}
}