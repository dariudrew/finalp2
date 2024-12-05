package br.ufal.ic.p2.jackut.modelo.exception;

public class NaoHaMensagemException extends Exception {
    public NaoHaMensagemException(){
        super("Não há mensagens.");
    }

}
