package br.ufal.ic.p2.jackut.modelo.sistemaControle;


import java.util.List;

import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.NaoHaRecadoException;
import br.ufal.ic.p2.jackut.modelo.exception.RecadoAhSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


public class SistemaConversa {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaConversa(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }


    public void enviarRecado(String id, String destino, String recado) throws AtributoNaoPreenchidoException, UsuarioNaoCadastradoException, RecadoAhSiMesmoException{
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
        Usuario usuarioDestino = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(destino));
        
        if(dados.validaNome(recado)){
        }
        List<String> novoRecado = usuarioDestino.getRecados();
     
        novoRecado.add(recado);
        usuarioDestino.setRecados(novoRecado);
        novoRecado = usuarioDestino.getRecados();
       
    }
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadoException {
        if (!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))) {
            throw new UsuarioNaoCadastradoException();
        }
    
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        List<String> recados = usuario.getRecados();

        if (recados == null || recados.isEmpty()) {
            throw new NaoHaRecadoException();
        }
    
     
        String recadoLido = recados.get(0);
        recados.remove(0); 
        usuario.setRecados(recados); 

    
        return recadoLido;
    }
    







}
