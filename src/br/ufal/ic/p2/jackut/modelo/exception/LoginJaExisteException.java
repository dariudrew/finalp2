package br.ufal.ic.p2.jackut.modelo.exception;

public class LoginJaExisteException extends Exception{

    public LoginJaExisteException(){
        super("Conta com esse nome já existe.");
    }
}
