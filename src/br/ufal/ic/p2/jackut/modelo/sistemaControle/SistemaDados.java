package br.ufal.ic.p2.jackut.modelo.sistemaControle;


import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;
import br.ufal.ic.p2.jackut.modelo.xml.XML;
import br.ufal.ic.p2.jackut.modelo.comunidade.Comunidade;
import br.ufal.ic.p2.jackut.modelo.exception.*;

public class SistemaDados {

     int contadorID = 1;
     int contadorIdComunidade = 1;
     public XML xml = new XML();
    

    Map<Integer, Usuario> usuariosPorID = new HashMap<>();
    Map<Integer, Comunidade> comunidadesPorID = new HashMap<>();


    public void zerarSistema(){
    
        usuariosPorID.clear();
        comunidadesPorID.clear();
        
        contadorID = 1;
        contadorIdComunidade = 1;
    }
    protected void validaDados(String login, String senha) throws LoginInvalidoException, SenhaInvalidaException{
        if(validaNome(login)){
            throw new LoginInvalidoException();
        }
        if(validaSenha(senha)){
            throw new SenhaInvalidaException();
        }
        
    }
    protected void validaCPF(String cpf){ //throws CPFInvalidoException 
        if(cpf == null || cpf.isEmpty() || !cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")){
            //throw new CPFInvalidoException();
        }
    }

    protected boolean validaEmail(String email) {
        return email == null || email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    protected boolean validaSenha(String senha) {
        return senha == null || senha.trim().isEmpty();
    }

    protected boolean validaEndereco(String endereco){
        return endereco == null || endereco.trim().isEmpty();
    }
    protected boolean validaNome(String nome){
        return nome == null || nome.isEmpty() || nome.isBlank();
    }
    public void encerrarSistema(){
        
    }
}
