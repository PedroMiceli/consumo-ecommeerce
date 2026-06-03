package com.consumo_ecommerce.consumo_ecommerce.exceptions;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String campo) {
        super(campo + " é obrigatório.");
    }

    public CampoObrigatorioException(String campo, boolean plural, boolean feminino) {
        super(plural ?
                campo + " são " + (feminino ? "obrigatórias" : "obrigatórios") :
                campo + " é " + (feminino ? "obrigatória" : "obrigatório"));
    }
}
