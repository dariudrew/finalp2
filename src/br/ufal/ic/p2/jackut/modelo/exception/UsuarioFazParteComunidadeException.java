package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioFazParteComunidadeException extends Exception{
    public UsuarioFazParteComunidadeException(){
        super("Usuario já faz parte dessa comunidade.");
    }

}
