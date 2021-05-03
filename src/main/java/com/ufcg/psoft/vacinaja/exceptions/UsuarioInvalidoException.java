package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de Usuario.
 */
public class UsuarioInvalidoException extends RuntimeException {
	public UsuarioInvalidoException(String message) {
        super(message);
	}
}
