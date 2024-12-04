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
        //verificar destino se existe
       // System.out.println("========= FUCAO ENVIAR RECADO ========");
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
       //     System.out.println("RECADO VAZIO OU NULO = FUNCAO ENVIAR RECADO");
        }
        List<String> novoRecado = usuarioDestino.getRecados();
      //  System.out.println("lista de recado antes");
        for(String s : novoRecado){
           // System.out.println(s);
        }
        novoRecado.add(recado);
        usuarioDestino.setRecados(novoRecado);
        novoRecado = usuarioDestino.getRecados();
       // System.out.println("lista de recado depoia");
        for(String s : novoRecado){
            //System.out.println(s);
        }
      //  System.out.println("========= FIMM   FUCAO ENVIAR RECADO ========");
    }
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadoException {
     //   System.out.println("===============FUNCAO LER RECADO ==================");
      //  System.out.println("");
    
        // Verificar se o ID é válido
        if (!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))) {
            throw new UsuarioNaoCadastradoException();
        }
    
        // Obter o usuário correspondente
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        List<String> recados = usuario.getRecados();
    
        // Verificar se há recados disponíveis
        if (recados == null || recados.isEmpty()) {
            throw new NaoHaRecadoException();
        }
    
        // Ler o primeiro recado
        String recadoLido = recados.get(0);
        recados.remove(0); // Remover o recado lido
        usuario.setRecados(recados); // Atualizar a lista no usuário
    
        // Log
       // System.out.println("Recado lido: " + recadoLido);
       // System.out.println("Recado retornado: " + recadoLido);
       // System.out.println("=============== FIM FUNCAO LER RECADO ==================");
        //System.out.println("");
    
        return recadoLido;
    }
    







}
