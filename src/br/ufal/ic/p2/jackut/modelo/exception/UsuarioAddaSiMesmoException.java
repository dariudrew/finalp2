package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioAddaSiMesmoException extends Exception {
    public UsuarioAddaSiMesmoException(){
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
