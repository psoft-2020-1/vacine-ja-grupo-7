package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de Funcionario.
 */
public class FuncionarioInvalidoException extends RuntimeException {

	public FuncionarioInvalidoException(String message){
        super(message);
	}
}
