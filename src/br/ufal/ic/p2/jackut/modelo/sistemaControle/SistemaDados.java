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
        xml.apagarConteudo();
    }

    protected void validaDados(String login, String senha) throws LoginInvalidoException, SenhaInvalidaException{
        if(validaNome(login)){
            throw new LoginInvalidoException();
        }
        if(validaSenha(senha)){
            throw new SenhaInvalidaException();
        }
        
    }

    protected boolean validaSenha(String senha) {
        return senha == null || senha.trim().isEmpty();
    }

    protected boolean validaNome(String nome){
        return nome == null || nome.isEmpty() || nome.isBlank();
    }
    public void encerrarSistema(){
        
    }
}
