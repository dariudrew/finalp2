package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhInimigoException extends Exception{
    public UsuarioJaEhInimigoException(){
        super("Usu�rio j� est� adicionado como inimigo.");
    }

}
