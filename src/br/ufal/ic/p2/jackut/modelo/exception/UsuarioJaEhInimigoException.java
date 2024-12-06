package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhInimigoException extends Exception{
    public UsuarioJaEhInimigoException(){
        super("Usuário já está adicionado como inimigo.");
    }

}
