package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhAmigoException extends Exception{
    public UsuarioJaEhAmigoException(){
        super("Usuário já está adicionado como amigo.");
    }

}
