package br.ufal.ic.p2.jackut.modelo.exception;

public class RecadoAhSiMesmoException extends Exception{
    public RecadoAhSiMesmoException(){
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }

}
