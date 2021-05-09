package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de Vacina.
 */
public class VacinaInvalidaException extends RuntimeException {
    /**
     * Chamada de construtor padrão de exceção.
     * @param message mensagem da exceção.
     */
    public VacinaInvalidaException(String message) {
        super(message);
    }
}
