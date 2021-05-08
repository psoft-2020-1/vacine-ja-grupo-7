package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Representa um erro de validação de um Administrador no sistema.
 * 
 * @author Natan
 *
 */
public class AdministradorInvalidoException extends RuntimeException {
	
	/**
	 * Construtor default.
	 * @param message messagem de erro.
	 */
	public AdministradorInvalidoException (String message) {
		super(message);
	}

}
