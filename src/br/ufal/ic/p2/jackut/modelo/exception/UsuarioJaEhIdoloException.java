package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioJaEhIdoloException extends Exception{
    public UsuarioJaEhIdoloException(){
        super("Usuário já está adicionado como ídolo.");
    }

}
