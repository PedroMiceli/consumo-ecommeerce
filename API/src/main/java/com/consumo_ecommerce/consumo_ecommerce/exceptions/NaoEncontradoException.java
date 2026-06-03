package com.consumo_ecommerce.consumo_ecommerce.exceptions;

public class NaoEncontradoException extends RuntimeException{
    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
