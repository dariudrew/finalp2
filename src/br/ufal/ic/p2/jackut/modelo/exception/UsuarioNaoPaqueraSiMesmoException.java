package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioNaoPaqueraSiMesmoException extends Exception {
    public UsuarioNaoPaqueraSiMesmoException(){
        super("Usu�rio n�o pode ser paquera de si mesmo.");
    }

}
