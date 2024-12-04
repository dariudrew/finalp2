package br.ufal.ic.p2.jackut.modelo.exception;

public class RecadoAhSiMesmoException extends Exception{
    public RecadoAhSiMesmoException(){
        super("Usuário não pode enviar recado para si mesmo.");
    }

}
