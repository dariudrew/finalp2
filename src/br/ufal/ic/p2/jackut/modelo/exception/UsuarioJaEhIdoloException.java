package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhIdoloException extends Exception{
    public UsuarioJaEhIdoloException(){
        super("Usu�rio j� est� adicionado como �dolo.");
    }

}
