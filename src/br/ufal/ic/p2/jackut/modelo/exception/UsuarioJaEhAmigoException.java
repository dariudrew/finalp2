package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhAmigoException extends Exception{
    public UsuarioJaEhAmigoException(){
        super("Usu�rio j� est� adicionado como amigo.");
    }

}
