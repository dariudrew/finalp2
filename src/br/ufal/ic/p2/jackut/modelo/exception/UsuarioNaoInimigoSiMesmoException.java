package br.ufal.ic.p2.jackut.modelo.exception;

public class UsuarioNaoInimigoSiMesmoException extends Exception{
    public UsuarioNaoInimigoSiMesmoException(){
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }

}
