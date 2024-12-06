package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioNaoPaqueraSiMesmoException extends Exception {
    public UsuarioNaoPaqueraSiMesmoException(){
        super("Usuário não pode ser paquera de si mesmo.");
    }

}
