package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioFaSiMesmoException extends Exception{
    public UsuarioFaSiMesmoException(){
        super("Usu�rio n�o pode ser f� de si mesmo.");
    }

}
