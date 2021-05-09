package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Representa um erro da validação da entidade Comorbidade.
 */

public class ComorbidadeInvalidaException extends RuntimeException {

    /**
     * Construtor default.
     * @param  message mensagem de erro.
     */

    public ComorbidadeInvalidaException (String message){
        super(message);
    }

}
