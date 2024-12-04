package br.ufal.ic.p2.jackut.modelo.exception;

public class LoginSenhaInvalidosException extends Exception{
    public LoginSenhaInvalidosException(){
        super("Login ou senha inválidos.");
    }

}
