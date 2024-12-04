package br.ufal.ic.p2.jackut.modelo.sistemaControle;

import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

import java.lang.reflect.Method;
import java.lang.reflect.Field;


import br.ufal.ic.p2.jackut.modelo.exception.*;;

public class SistemaUsuario {
    private SistemaDados dados;
    int ID;


    public SistemaUsuario(SistemaDados dados){
        this.dados = dados;
    }


    public void criarUsuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException, LoginJaExisteException, AtributoNaoPreenchidoException{
        //tratar as strings
        dados.validaDados(login, senha);
        if(getIdUsuario(login) != 0){
            throw new LoginJaExisteException();
        }

        Usuario usuario = new Usuario(dados.contadorID, login, senha, nome);
        dados.usuariosPorID.put(usuario.getID(), usuario);
        //System.out.println("testando nome: "+ usuario.getNome());
        dados.contadorID++;
    }


    public int abrirSessao(String login, String senha) throws LoginSenhaInvalidosException{
       
        for(Usuario usuario : dados.usuariosPorID.values()){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return usuario.getID();
            }
        }
        throw new LoginSenhaInvalidosException(); //retornar o id da sessao? ou usuario?
        
    }


    public String getAtributoUsuario(String login, String atributo) 
    throws UsuarioNaoCadastradoException, LoginInvalidoException, 
           SenhaInvalidaException, AtributoNaoPreenchidoException, IllegalArgumentException, IllegalAccessException {

    ID = getIdUsuario(login);

if (dados.usuariosPorID.containsKey(ID)) {
    Usuario usuario = dados.usuariosPorID.get(ID);

    try {
        // Obtém o campo correspondente ao nome do atributo
        Field campo = Usuario.class.getDeclaredField(atributo);
        campo.setAccessible(true); // Permite acessar atributos privados
        
        // Obtém o valor do campo
        Object valor = campo.get(usuario);
        
        if (valor == null || valor == "") {
            throw new AtributoNaoPreenchidoException();
        }

        return valor.toString();
    } catch (Exception e) {
        throw new AtributoNaoPreenchidoException();
    } 
} else {
    throw new UsuarioNaoCadastradoException();
}
}

    public void editarPerfil(String id, String atributo, String valor) throws UsuarioNaoCadastradoException{
       
       if(dados.usuariosPorID.isEmpty())
       {
        throw new UsuarioNaoCadastradoException();
       }
        //ID = Integer.valueOf(id);
      
        if(!dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        

        switch (atributo){
            case "nome":
                usuario.setNome(valor);
            case "descricao":
                usuario.setDescricao(valor);
            case "estadoCivil":
                usuario.setEstadoCivil(valor);
            case "aniversario":
                usuario.setAniversario(valor);
            case "filhos":
                usuario.setFilhos(valor);
            case "idiomas":
                usuario.setIdiomas(valor);
            case "cidadeNatal":
                usuario.setCidadeNatal(valor);
            case "estilo":
                usuario.setEstilo(valor);
            case "fumo":
                usuario.setFumo(valor);
            case "bebo":
                usuario.setBebo(valor);
            case "moro":
                usuario.setMoro(valor);
        }
    }

    public void adicionarAmigo(String id, String amigo) throws UsuarioAddaSiMesmoException, UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, LoginInvalidoException, UsuarioJaEhAmigoException, UsuarioAdicionadoEsperandoAceitarException{
      //  System.out.println("================================ FUNCAO ADD AMIGO ===========================");
       // System.out.println("");
       // System.out.println(""); 
       // System.out.println(""); 
        if(!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.containsKey(getIdUsuario(amigo))){
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
       // System.out.println("LOGIN: "+usuario.getLogin()+" QUER ADICIONAR: "+amigo);

        if(usuario.getLogin().equals(amigo)){
            throw new UsuarioAddaSiMesmoException();
        }
        
        String solicitacoes = usuario.getSolicitacoesEnviadas();
        if(ehAmigo(usuario.getLogin(), amigo)){
        //    System.out.println(usuario.getLogin()+" E "+amigo+" SAO AMIGOS");
            throw new UsuarioJaEhAmigoException();
        }
        else if(verificaAmizade(solicitacoes, amigo)){
          //  System.out.println(usuario.getLogin()+" JA TEM "+amigo+" NA LISTA DE AMIGOS, ESPERANDO ACEITAR");
            throw new UsuarioAdicionadoEsperandoAceitarException();
        }

     
       // System.out.println("LISTA DE AMIGOS SOLICITADOS do "+usuario.getLogin()+" RETORNADA: "+ solicitacoes);
        solicitacoes = solicitacoes.replaceAll("^\\{", "").replaceAll("\\}$", "");
       // System.out.println("LISTA APOS REMOÇAO DO {} <"+solicitacoes+">");
        
        if(solicitacoes.isEmpty()){
            usuario.setSolicitacoesEnviadas("{"+amigo+"}");       
            //System.out.println("COMO A LISTA TA FAZIA COLOCA DIRETO E FICA ASSIM");
            //System.out.println(usuario.getLogin()+" ADD "+amigo);
           // System.out.println("lista: "+usuario.getSolicitacoesEnviadas());
        }
        else{
            solicitacoes = "{".concat(solicitacoes).concat(","+amigo+"}"); //adicona o amigo no final da lista
            usuario.setSolicitacoesEnviadas(solicitacoes);  //att o xml
           // System.out.println("COMO A LISTA tem gente FICA ASSIM");
           // System.out.println(usuario.getLogin()+" ADD "+solicitacoes);
        }
        if(verificaAmizade(usuario.getSolicitacoesEnviadas(), amigo)){
           Usuario usuario1 = dados.usuariosPorID.get(getIdUsuario(amigo));
          // System.out.println("EU MANDEI CONVITE PARA: "+amigo);
           if(verificaAmizade(usuario1.getSolicitacoesEnviadas(), usuario.getLogin())){
           // System.out.println(amigo+" TAMBEM ME MANDOU SOLICITACAO");
                usuario.setAmigos(atualizarAmigos(usuario.getAmigos(), amigo));
                usuario1.setAmigos(atualizarAmigos(usuario1.getAmigos(), usuario.getLogin()));
              //  System.out.println(usuario.getLogin()+" MINHA LISTA DE AMIGOS: "+usuario.getAmigos());
              //  System.out.println(usuario1.getLogin()+" A LISTA Do AMIGOS: "+usuario1.getAmigos());
                //atualizar solicitacoes enviadas

               // System.out.println(usuario.getLogin()+" MINHA LISTA DE SOLICITACOA ANTES DE ATT: "+usuario.getSolicitacoesEnviadas());
              //  System.out.println(usuario1.getLogin()+" LISTA DE SOLICITACOA do amigo ANTES DE ATT: "+usuario1.getSolicitacoesEnviadas());
                usuario.setSolicitacoesEnviadas(atualizarSolicitacoes(usuario.getSolicitacoesEnviadas(), amigo));
                usuario1.setSolicitacoesEnviadas(atualizarSolicitacoes(usuario1.getSolicitacoesEnviadas(), usuario.getLogin()));
               // System.out.println(usuario.getLogin()+" MINHA LISTA DE SOLICITACOA DEPOIS DE ATT: "+usuario.getSolicitacoesEnviadas());
               // System.out.println(usuario1.getLogin()+" LISTA DE SOLICITACOA do amigo DEPOIS DE ATT: "+usuario1.getSolicitacoesEnviadas());

           }

        }
        //System.out.println("");
       // System.out.println(""); 
       // System.out.println("");                                
       // System.out.println("================================ FIM FUNCAO ADD AMIGO ===========================");
        
    }
    public boolean ehAmigo(String login, String amigo) throws AtributoNaoPreenchidoException, LoginInvalidoException{
        //System.out.println("============================= FUNCAO EH AMIGO ==========================");
        if(dados.validaNome(login)){
            throw new LoginInvalidoException();
        }
       // System.out.println("============================= verifica FIM FUNCAO EH AMIGO ==========================");
        return verificaAmizade(getAmigos(login), amigo);

    }
    
    public String getAmigos(String login) throws LoginInvalidoException, AtributoNaoPreenchidoException{
        //System.out.println("============================ FUNCAO GET AMIGO ===========================");
        //System.out.println("");
        if(dados.validaNome(login)){
            throw new LoginInvalidoException();
        }
        ID = getIdUsuario(login);
        Usuario usuario = dados.usuariosPorID.get(ID);
      //  System.out.println("LISTA DE AMIGOS: "+usuario.getAmigos());
       // System.out.println("");
      //  System.out.println("============================ FIM FUNCAO GET AMIGO ===========================");
        return usuario.getAmigos();
    }


   
    public int getIdUsuario(String atributo) throws AtributoNaoPreenchidoException {
        try {
            

            if(!dados.usuariosPorID.isEmpty())
            {
                for (Usuario usuario : dados.usuariosPorID.values()) {
                    Method metodo = usuario.getClass().getMethod("getLogin");

                    if (metodo.invoke(usuario).equals(atributo)) {
                        return usuario.getID();
                    }
                }
            }

        } catch (Exception e) {
            throw new AtributoNaoPreenchidoException();
        }
        return 0;
    }
    
    private boolean verificaAmizade(String listaAmigos, String amigo) throws LoginInvalidoException, AtributoNaoPreenchidoException{
       // System.out.println("======  FUNÇÃO VERIFICA AMIZADE ========");
       // System.out.println("A LISTA CHEGOU ASSIM: "+listaAmigos);
        listaAmigos = listaAmigos.replaceAll("^\\{", "").replaceAll("\\}$", "");
        //System.out.println("A LISTA apos chaves ASSIM: "+listaAmigos);
        if(listaAmigos.isEmpty()){
           // System.out.println("====== vazia -  FIM DA FUNÇÃO VERIFICA AMIZADE ========");
            return false;
        }
        if(listaAmigos.contains(",")){
    
            String[] listaAmigosArray = listaAmigos.split(",");
           // System.out.println("A LISTA apos split ASSIM: ");
            for(String s: listaAmigosArray){
                //System.out.println(s);
            }
            for(String loginAmigo : listaAmigosArray){
                if(loginAmigo.equals(amigo)){
                  //  System.out.println("====== true FIM DA FUNÇÃO VERIFICA AMIZADE ========");
                    return true;
                }
            }
           // System.out.println("====== false FIM DA FUNÇÃO VERIFICA AMIZADE ========");
        }
       // System.out.println("A LISTA so tem um elemento ASSIM: "+listaAmigos);
        if(listaAmigos.equals(amigo)){
          //  System.out.println("====== true FIM DA FUNÇÃO VERIFICA AMIZADE ========");
            return true;
        }
        
        return false;
    }
    
    private String atualizarAmigos(String listaAmigos, String amigo){
      //  System.out.println("======  FUNÇÃO ATT AMIGO ========");
       // System.out.println("LISTA CHEGOU ASSSIM: "+listaAmigos);
        if(listaAmigos.equals("{}")){
            listaAmigos = listaAmigos.replaceAll("\\}$", amigo+"}");
           // System.out.println("LISTA apos o 1 if ASSSIM: "+listaAmigos);
        }
        else{
            listaAmigos = listaAmigos.replaceAll("\\}$",","+amigo+"}");
           // System.out.println("LISTA apos o else ASSSIM: "+listaAmigos);
        }
        //System.out.println("LISTA final ASSSIM: "+listaAmigos);
       // System.out.println("====== FIM da FUNÇÃO ATT AMIGO ========");
        return listaAmigos;
        
    }
    
    private String atualizarSolicitacoes(String solicitacoes, String amigo){
        //System.out.println("======  FUNÇÃO ATT SOLICITACOES ========");
        solicitacoes = solicitacoes.replaceAll("^\\{", "").replaceAll("\\}$", "");
       // System.out.println("SOLICITACOES APOS CHAVES: "+solicitacoes);
        String solicitacoesAtualizada = "{}";
        if(solicitacoes.contains(",")){
            String[] solicitacoessArray = solicitacoes.split(",");
           // System.out.println("SOLICITACOES APOS split: "+solicitacoessArray);
            for(String loginAmigo : solicitacoessArray){
                if(loginAmigo.equals(amigo)){
                    continue;
                }
                if(solicitacoesAtualizada.equals("{}")){
                    solicitacoesAtualizada = solicitacoesAtualizada.replaceAll("^\\{", "").replaceAll("\\}$", "");
                }
                solicitacoesAtualizada = solicitacoesAtualizada.concat(loginAmigo).concat(",");
                
            }
           // System.out.println("A NOVA VARIAVEL SOLICITACOES APOS EXCLUIR O AMIGO: "+solicitacoesAtualizada);
            solicitacoesAtualizada = "{".concat(solicitacoesAtualizada.replaceAll(",$", "}"));
           // System.out.println("A NOVA VARIAVEL SOLICITACOES APOS add chaves: "+solicitacoesAtualizada);
        }
        
        
       // System.out.println("======  FIM da claerFUNÇÃO ATT SOLICITACOES ========");
        return solicitacoesAtualizada;
    }
}
