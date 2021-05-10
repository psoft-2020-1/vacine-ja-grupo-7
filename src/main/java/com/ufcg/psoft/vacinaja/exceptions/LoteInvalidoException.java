package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de Lote.
 */
public class LoteInvalidoException extends RuntimeException {
	
	public LoteInvalidoException(String message) {
		super(message);
	}
	
}
