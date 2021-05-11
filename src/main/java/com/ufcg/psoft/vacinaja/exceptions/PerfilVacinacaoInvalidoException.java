package com.ufcg.psoft.vacinaja.exceptions;

/**
 * Classe de exceção para erros em tempo de execução de perfil de vacinação.
 */
public class PerfilVacinacaoInvalidoException extends RuntimeException {

    public PerfilVacinacaoInvalidoException(String message){
        super(message);
    }


}
