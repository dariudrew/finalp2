package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhPaqueraException extends Exception {
    public UsuarioJaEhPaqueraException(){
        super("Usu�rio j� est� adicionado como paquera.");
    }

}
