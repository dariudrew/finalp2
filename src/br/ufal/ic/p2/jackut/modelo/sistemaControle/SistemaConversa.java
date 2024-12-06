package br.ufal.ic.p2.jackut.modelo.sistemaControle;


import java.util.List;

import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.FuncaoInvalidaEhMeuInimigoException;
import br.ufal.ic.p2.jackut.modelo.exception.NaoHaRecadoException;
import br.ufal.ic.p2.jackut.modelo.exception.RecadoAhSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.usuario.Relacionamentos;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


public class SistemaConversa {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaConversa(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
     
    }

    public void enviarRecado(String id, String destino, String recado) throws AtributoNaoPreenchidoException, UsuarioNaoCadastradoException, RecadoAhSiMesmoException, FuncaoInvalidaEhMeuInimigoException{
        if(!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(destino))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        
        if(usuario.getLogin().equals(destino)){
            throw new RecadoAhSiMesmoException();
        }
        else if(boolIni(String.valueOf(sistemaUsuario.getIdUsuario(destino)), usuario.getLogin())){
            throw new FuncaoInvalidaEhMeuInimigoException();
        }
        Usuario usuarioDestino = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(destino));
        Relacionamentos relacionamentos = usuarioDestino.getRelacionamentos();
        
        if(dados.validaNome(recado)){
        }
        List<String> novoRecado = relacionamentos.getRecados();

     
        novoRecado.add(recado);
        relacionamentos.setRecados(novoRecado);
        novoRecado = relacionamentos.getRecados();
        dados.xml.editarUsuario(usuario);
        dados.xml.editarUsuario(usuarioDestino);
       
    }
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadoException {
        if (!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))) {
            throw new UsuarioNaoCadastradoException();
        }
    
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        List<String> recados = relacionamentos.getRecados();

        if (recados == null || recados.isEmpty()) {
            throw new NaoHaRecadoException();
        }
    
     
        String recadoLido = recados.get(0);
        recados.remove(0); 
        relacionamentos.setRecados(recados); 

        dados.xml.editarUsuario(usuario);
        return recadoLido;
    }
    
    public boolean boolIni(String id, String inimigo){

        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();

        String listaInimigos = relacionamentos.getInimigos();
        System.out.println("LISTA DE INIMIGOS: "+listaInimigos);
        listaInimigos = listaInimigos.replaceAll("^\\{", "").replaceAll("\\}$", "");
        if(listaInimigos.isEmpty()){
            return false;
        }
        if(listaInimigos.contains(",")){
    
            String[] listaInimigosArray = listaInimigos.split(",");
          
            for(String loginInimigos : listaInimigosArray){
                if(loginInimigos.equals(inimigo)){
                    return true;
                }
            }
          
        }
       
        if(listaInimigos.equals(inimigo)){
            return true;
        }
        
        return false;
    }






}
