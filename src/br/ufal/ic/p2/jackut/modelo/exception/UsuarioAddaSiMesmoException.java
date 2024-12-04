package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioAddaSiMesmoException extends Exception {
    public UsuarioAddaSiMesmoException(){
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
