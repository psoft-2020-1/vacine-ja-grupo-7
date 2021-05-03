package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Representa um erro da validação da entidade Cidadão.
 */

public class CidadaoInvalidoException extends RuntimeException {

    /**
     * Construtor default.
     * @param  message mensagem de erro.
     */

    public CidadaoInvalidoException(String message){
        super(message);
    }

}
