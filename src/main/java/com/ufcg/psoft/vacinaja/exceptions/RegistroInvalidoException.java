package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de Registro de Vacinação.
 */
public class RegistroInvalidoException extends RuntimeException {

    public RegistroInvalidoException(String message){
        super(message);
    }
}

