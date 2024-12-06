package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioFaSiMesmoException extends Exception{
    public UsuarioFaSiMesmoException(){
        super("Usuário não pode ser fã de si mesmo.");
    }

}
