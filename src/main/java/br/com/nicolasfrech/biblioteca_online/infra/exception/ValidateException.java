package br.com.nicolasfrech.biblioteca_online.infra.exception;

public class ValidateException extends RuntimeException {
    public ValidateException(String mensagem) {
        super(mensagem);
    }
}

