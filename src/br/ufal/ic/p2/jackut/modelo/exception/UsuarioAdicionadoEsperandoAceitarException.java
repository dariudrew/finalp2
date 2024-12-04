package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioAdicionadoEsperandoAceitarException extends Exception {
    public UsuarioAdicionadoEsperandoAceitarException(){
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
    

}
